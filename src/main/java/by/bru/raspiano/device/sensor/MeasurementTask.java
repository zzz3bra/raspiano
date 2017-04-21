package by.bru.raspiano.device.sensor;

import by.bru.raspiano.device.sensor.sht21.*;
import by.bru.raspiano.domain.enumeration.SensorType;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Task that periodically executes the temperature and
 * humidity measurements.
 */
public final class MeasurementTask {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementTask.class);
    /**
     * The {@link SHT21} implementation.
     */
    private final SHT21 sht21;
    /**
     * Start delay in seconds.
     */
    private final int delay;
    /**
     * Measurement interval in seconds.
     */
    private final int interval;
    /**
     * The latest {@link SHT21Status}.
     */
    private SHT21Status latestStatus;

    private final List<MeasurementTaskListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Create a new {@link MeasurementTask}.
     *
     * @param delay    The start delay in seconds.
     * @param interval The measurement interval in seconds.
     */
    private MeasurementTask(final SHT21 sht21, final int delay, final int interval) {
        this.sht21 = sht21;
        this.delay = delay;
        this.interval = interval;
        this.initializeAndStart();
    }

    /**
     * Create a {@link MeasurementTask}.
     *
     * @param startDelay The start delay in seconds.
     * @param interval   The measurement interval in seconds.
     * @return A new {@link MeasurementTask}.
     * @throws IllegalArgumentException if
     *                                  <ul>
     *                                  <li>{@code startDelay} is less than zero seconds</li>
     *                                  <li>{@code intertval} is less than 10 seconds</li>
     *                                  </ul>
     */
    public static MeasurementTask create(final SHT21 sht21, final int startDelay, final int interval) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Start delay must be greater than or equal to zero. Value: " + startDelay);
        }
        if (interval < 10) {
            throw new IllegalArgumentException("Minimum interval is 10 seconds. Value: " + interval);
        }
        return new MeasurementTask(sht21, startDelay, interval);
    }

    private void initializeAndStart() {

        final TimerTask action = new TimerTask() {
            I2cSensorDTO tempSensor = sht21.getSensors().stream()
                .filter(sensor -> sensor.getSensorType().equals(SensorType.TEMPERATURE)).findFirst().get();
            I2cSensorDTO humiditySensor = sht21.getSensors().stream()
                .filter(sensor -> sensor.getSensorType().equals(SensorType.HUMIDITY)).findFirst().get();

            @Override
            public void run() {
                LOG.debug("Starting SHT-21 measurements");
                final HeaterStatus heaterStatus = sht21.getHeaterStatus();
                final EndOfBatteryAlert endOfBatteryAlert = sht21.getEndOfBatteryAlert();

                try {
                    final Measurement tempMeasurement = sht21.measurePoll(MeasureType.TEMPERATURE);
                    final Measurement humidityMeasurement = sht21.measurePoll(MeasureType.HUMIDITY);
                    final Resolution resolution = sht21.getResolution();
                    MeasurementTask.this.latestStatus = SHT21Status.create(heaterStatus, endOfBatteryAlert,
                        tempMeasurement, humidityMeasurement, resolution);
                    for (final MeasurementTaskListener listener : listeners) {
                        listener.onReceived(tempMeasurement, tempSensor);
                        listener.onReceived(humidityMeasurement, humiditySensor);
                    }
                } catch (final UnsupportedMeasureTypeException exception) {
                    LOG.error(exception.getMessage(), exception);
                }

            }
        };

        final Timer caretaker = new Timer();
        caretaker.schedule(action, TimeUnit.SECONDS.toMillis(this.delay), TimeUnit.SECONDS.toMillis(this.interval));
    }

    /**
     * Return the {@link java.util.Date} this {@link by.bru.raspiano.device.sensor.sht21.SHT21Status} was created at.
     *
     * @return The {@link java.util.Date} this {@link by.bru.raspiano.device.sensor.sht21.SHT21Status} was created at.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Date getCreatedAt() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No timestamp information available.");
        }
        return this.latestStatus.getCreatedAt();
    }

    /**
     * Return the {@link by.bru.raspiano.device.sensor.sht21.HeaterStatus}.
     *
     * @return The {@link by.bru.raspiano.device.sensor.sht21.HeaterStatus}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public HeaterStatus getHeaterStatus() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No heater information available.");
        }
        return this.latestStatus.getHeaterStatus();
    }

    /**
     * Return the {@link by.bru.raspiano.device.sensor.sht21.EndOfBatteryAlert}.
     *
     * @return The {@link by.bru.raspiano.device.sensor.sht21.EndOfBatteryAlert}.
     * @throws IllegalStateException if task did not execute until now.
     */

    public EndOfBatteryAlert getEobStatus() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No end-of-battery information available.");
        }
        return this.latestStatus.getEobStatus();
    }

    /**
     * Return the temperature {@link by.bru.raspiano.device.sensor.Measurement}.
     *
     * @return The temperature {@link by.bru.raspiano.device.sensor.Measurement}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Measurement getTemperature() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No temperature information available.");
        }
        return this.latestStatus.getTemperature();
    }

    /**
     * Return the humidity {@link by.bru.raspiano.device.sensor.Measurement}.
     *
     * @return The humidity {@link by.bru.raspiano.device.sensor.Measurement}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Measurement getHumidity() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No humidity information available.");
        }
        return this.latestStatus.getHumidity();
    }

    /**
     * Return the {@link by.bru.raspiano.device.sensor.sht21.Resolution}.
     *
     * @return The {@link by.bru.raspiano.device.sensor.sht21.Resolution}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Resolution getResolution() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No resolution information available.");
        }
        return this.latestStatus.getResolution();
    }

    /**
     * Register a {@link MeasurementTaskListener} for receiving updates.
     *
     * @param listener A non-null {@link MeasurementTaskListener}.
     * @return
     */
    public boolean addListener(final MeasurementTaskListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        if (this.listeners.contains(listener)) {
            return false;
        }
        return this.listeners.add(listener);
    }

}
