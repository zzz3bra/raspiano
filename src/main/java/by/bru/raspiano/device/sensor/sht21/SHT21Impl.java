package by.bru.raspiano.device.sensor.sht21;

import by.bru.raspiano.device.sensor.MeasureType;
import by.bru.raspiano.device.sensor.Measurement;
import by.bru.raspiano.device.sensor.UnsupportedMeasureTypeException;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import com.google.common.collect.Lists;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link by.bru.raspiano.device.sensor.sht21.SHT21}.
 */
public final class SHT21Impl implements SHT21 {

    private static final Logger log = LoggerFactory.getLogger(SHT21Impl.class);

    /**
     * The SHT21 as I2C device.
     */
    private I2CDevice device;

    /**
     * The SHT21 can provide abstraction for multiple hardware sensors.
     */
    private List<I2cSensorDTO> i2cSensors;

    /**
     * Creates a new SHT21 device.
     *
     * @param bus     The I2C bus number, either 0 or 1.
     * @param address Address of the device on the I2C bus.
     * @param sensors Domain sensor representation.
     */
    private SHT21Impl(final int bus, final int address, final I2cSensorDTO... sensors) {
        try {
            final I2CBus i2CBus;
            if (bus == 0) {
                i2CBus = I2CFactory.getInstance(I2CBus.BUS_0);
            } else if (bus == 1) {
                i2CBus = I2CFactory.getInstance(I2CBus.BUS_1);
            } else {
                throw new I2CFactory.UnsupportedBusNumberException();
            }
            this.device = i2CBus.getDevice(address);
            this.i2cSensors = Lists.newArrayList(sensors);
        } catch (final I2CFactory.UnsupportedBusNumberException | IOException exception) {
            this.device = null;
            log.error(exception.getMessage(), exception);
        }
    }

    /**
     * Creates a new SHT21 device.
     *
     * @param bus     The I2C bus number, either 0 or 1.
     * @param address Address of the device on the I2C bus.
     * @param sensors Domain sensor representation.
     * @return The new SHT21 device
     */
    public static SHT21Impl create(final int bus, final int address, final I2cSensorDTO... sensors) {
        if (bus < 0 || bus > 1) {
            throw new IllegalArgumentException("Bus not valid.");
        }
        return new SHT21Impl(bus, address, sensors);
    }

    /**
     * Sleep for a certain amount of time.
     *
     * @param milliseconds Milliseconds to sleep.
     * @throws IllegalArgumentException if {@code milliseconds} is less than zero.
     */
    private static void delay(final long milliseconds) {
        assert milliseconds >= 0;
        if (milliseconds == 0) {
            return;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Calculates the relative humidity.
     * The relative humidity RH is obtained by the following formula (result in
     * %RH), no matter which resolution is chosen:
     * RH = - 6 + 125 x (S_RH/2^16)
     *
     * @param buffer containing two bytes (humidity raw value, 16bit scaled)
     * @return relative humidity [%RH]
     */
    static float calcRH(final ByteBuffer buffer) {
        assert buffer != null;
        int sRH = buffer.getShort(0);
        // clear bits [1..0] (status bits)
        sRH &= ~0x0003;
        sRH &= 0xffff;
        return (float) (-6 + 125 * (sRH / Math.pow(2, 16)));
    }

    /**
     * Calculates temperature.
     *
     * @param buffer Buffer containing two bytes (temperature raw value, 16bit scaled)
     * @return temperature [deg C]
     */

    static float calcTemperatureC(final ByteBuffer buffer) {
        assert buffer != null;
        short sTemp = buffer.getShort(0);
        sTemp &= ~0x0003;
        return -46.85f + 175.72f / 65536 * (float) sTemp;
    }

    /**
     * Calculates checksum for n bytes of data and compares it with expected checksum.
     *
     * @param data checksum is built based on this data
     * @return {@code true}, if calculated and reported checksum match. Otherwise, {@code false}
     * is returned.
     */
    static boolean checkCrC(final byte data[]) {
        assert data != null;
        final short POLYNOMIAL = 0x131;
        int crc = 0;
        final byte checksum = data[2];

        for (int byteCtr = 0; byteCtr < data.length - 1; ++byteCtr) {
            crc ^= data[byteCtr];
            for (int bit = 8; bit > 0; --bit) {
                if ((crc & 0x80) == 0x80) {
                    crc = (crc << 1) ^ POLYNOMIAL;
                } else {
                    crc = crc << 1;
                }
            }
        }

        if ((byte) crc == checksum) {
            log.debug("Checksum matches");
            return true;
        } else {
            log.debug("Checksum does not match. Expected : " + checksum + ". Calculated: " + (byte) crc);
            return false;
        }
    }

    /**
     * Performs a reset.
     */
    void softReset() {
        try {
            log.debug("Writing byte " + String.format("0x%02X", Command.SOFT_RESET.getCommandByte()) + " to device " + this.device);
            this.device.write(Command.SOFT_RESET.getCommandByte());
        } catch (final IOException exception) {
            log.error("SoftReset failed with an IOException: ", exception);
        }
        delay(50);
        log.trace("Finished softReset()");
    }

    @Override
    public Resolution getResolution() {
        this.softReset();
        try {
            log.debug("Writing byte " + String.format("0x%02X", Command.USER_REG_R.getCommandByte()) + " to device " + this.device);
            this.device.write(Command.USER_REG_R.getCommandByte());
            delay(100);
            final byte[] bytes = new byte[1];
            this.device.read(bytes, 0, 1);
            return Resolution.getResolution(bytes[0]);
        } catch (final IOException exception) {
            log.error("Getting resolution failed because of an IOException: ", exception);
        }
        return null;
    }

    @Override
    public EndOfBatteryAlert getEndOfBatteryAlert() {
        log.debug("Starting getEndOfBatteryAlert()");
        this.softReset();
        try {
            log.trace("Writing byte " + String.format("0x%02X", Command.USER_REG_R.getCommandByte()) + " to device " + this.device);
            this.device.write(Command.USER_REG_R.getCommandByte());
            delay(100);

            final byte[] bytes = new byte[1];
            this.device.read(bytes, 0, 1);
            return EndOfBatteryAlert.getEOBAlert(bytes[0]);
        } catch (final IOException exception) {
            log.error("getEOBAlert() failed.", exception);
        }
        return null;

    }

    @Override
    public HeaterStatus getHeaterStatus() {
        this.softReset();
        try {
            log.debug("Writing byte " + String.format("0x%02X", Command.USER_REG_R.getCommandByte()) + " to device " + this.device);
            this.device.write(Command.USER_REG_R.getCommandByte());
            delay(100);

            final byte[] bytes = new byte[1];
            this.device.read(bytes, 0, 1);
            return HeaterStatus.getStatus(bytes[0]);
        } catch (final IOException exception) {
            log.error("Getting heater status failed. IOException: ", exception);
        }
        return null;
    }

    @Override
    public Measurement measurePoll(final MeasureType measureType) throws UnsupportedMeasureTypeException {
        switch (measureType) {
            case HUMIDITY: {
                return Measurement.create(this.measurePollHumidity(), MeasureType.HUMIDITY);
            }
            case TEMPERATURE: {
                return Measurement.create(this.measurePollTemperature(), MeasureType.TEMPERATURE);
            }
            default:
                throw new UnsupportedMeasureTypeException("MeasureType not supported: " + measureType);
        }
    }

    @Override
    public List<I2cSensorDTO> getSensors() {
        return i2cSensors;
    }

    private float measurePollTemperature() {
        this.softReset();
        try {
            this.device.write(Command.TRIG_T_MEASUREMENT_POLL.getCommandByte());
            delay(260);
            final byte[] bytes = new byte[3];
            this.device.read(bytes, 0, 3);
            final ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put(bytes[0]);
            buffer.put((byte) (bytes[1] & 0xFC));

            if (checkCrC(bytes)) {
                return calcTemperatureC(buffer);
            } else {
                return Float.MIN_VALUE;
            }

        } catch (final IOException exception) {
            log.error("Temperature measurement failed because of an IOException: ", exception);
            return Float.MIN_VALUE;
        }


    }

    private float measurePollHumidity() {
        this.softReset();
        try {
            this.device.write(Command.TRIG_RH_MEASUREMENT_POLL.getCommandByte());
            delay(60);
            final byte[] bytes = new byte[3];
            this.device.read(bytes, 0, 3);
            final ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put(bytes[0]);
            buffer.put((byte) (bytes[1] & 0xFC));

            if (checkCrC(bytes)) {
                return calcRH(buffer);
            } else {
                return Float.MIN_VALUE;
            }

        } catch (final IOException exception) {
            log.error("Humidity measurement failed because of an IOException: ", exception);
            return Float.MIN_VALUE;
        }
    }

}
