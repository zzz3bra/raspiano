package by.bru.raspiano.device.sensor.sht21;

import by.bru.raspiano.device.sensor.MeasureType;
import by.bru.raspiano.device.sensor.Measurement;
import by.bru.raspiano.device.sensor.UnsupportedMeasureTypeException;

/**
 * @author Stefan Freitag
 */
public interface SHT21 {

    int I2C_ADDRESS = 0x40;
    /**
     * Default start delay in seconds.
     */
    int DEFAULT_DELAY = 5;
    /**
     * Default measurement interval in seconds.
     */
    int DEFAULT_INTERVAL = 10;

    /**
     * Return the present resolution (in bits) for temperature and humidity measurement.
     *
     * @return Present  {@link Resolution}.
     */
    Resolution getResolution();

    /**
     * Return the status of the {@link EndOfBatteryAlert}.
     *
     * @return Present {@link EndOfBatteryAlert} status.
     */
    EndOfBatteryAlert getEndOfBatteryAlert();

    /**
     * Return the {@link HeaterStatus}.
     *
     * @return Present {@link HeaterStatus}.
     */
    HeaterStatus getHeaterStatus();

    /**
     * Measures humidity or temperature.
     *
     * @param measureType Either temperature or humidity measurement.
     * @return A {@link Measurement}.
     * @throws UnsupportedMeasureTypeException if an unsupported measure type was specified.
     */
    Measurement measurePoll(MeasureType measureType) throws UnsupportedMeasureTypeException;

}
