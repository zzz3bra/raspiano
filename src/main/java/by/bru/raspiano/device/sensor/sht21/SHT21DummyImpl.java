package by.bru.raspiano.device.sensor.sht21;

import by.bru.raspiano.device.sensor.MeasureType;
import by.bru.raspiano.device.sensor.Measurement;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import java.util.List;
import java.util.Random;

/**
 * A dummy implementation of the SHT21 interface.
 */
public final class SHT21DummyImpl implements SHT21 {

    private static final Random RAND = new Random();

    /**
     * The SHT21 can provide abstraction for multiple hardware sensors.
     */
    private List<I2cSensorDTO> i2cSensors;

    private SHT21DummyImpl(final int bus, final int address, final List<I2cSensorDTO> sensors) {
        this.i2cSensors = sensors;
    }

    public static SHT21DummyImpl create(final int bus, final int address, final List<I2cSensorDTO> sensors) {
        return new SHT21DummyImpl(bus, address, sensors);

    }

    /**
     * Return the present resolution (in bits) for temperature and humidity measurement.
     *
     * @return Present  {@link by.bru.raspiano.device.sensor.sht21.Resolution}.
     */
    @Override
    public Resolution getResolution() {
        final int i = RAND.nextInt(Integer.MAX_VALUE) % Resolution.values().length;
        return Resolution.values()[i];
    }

    /**
     * Return the status of the {@link by.bru.raspiano.device.sensor.sht21.EndOfBatteryAlert}.
     *
     * @return Present {@link by.bru.raspiano.device.sensor.sht21.EndOfBatteryAlert} status.
     */
    @Override
    public EndOfBatteryAlert getEndOfBatteryAlert() {
        if (RAND.nextInt() % 2 == 0) {
            return EndOfBatteryAlert.EOB_ALERT_OFF;
        }
        return EndOfBatteryAlert.EOB_ALERT_ON;
    }

    /**
     * Return the {@link by.bru.raspiano.device.sensor.sht21.HeaterStatus}.
     *
     * @return Present {@link by.bru.raspiano.device.sensor.sht21.HeaterStatus}.
     */
    @Override
    public HeaterStatus getHeaterStatus() {
        if (RAND.nextInt() % 2 == 0) {
            return HeaterStatus.HEATER_OFF;
        }
        return HeaterStatus.HEATER_ON;
    }

    /**
     * Measures humidity or temperature.
     *
     * @param measureType Either temperature or humidity measurement.
     * @return A {@link by.bru.raspiano.device.sensor.Measurement}.
     */
    @Override
    public Measurement measurePoll(final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException(MeasureType.class.getSimpleName() + " is null");
        }
        return Measurement.create(Math.abs(RAND.nextFloat()), measureType);
    }

    @Override
    public List<I2cSensorDTO> getSensors() {
        return i2cSensors;
    }
}
