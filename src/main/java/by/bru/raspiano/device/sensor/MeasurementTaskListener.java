package by.bru.raspiano.device.sensor;

import by.bru.raspiano.service.dto.I2cSensorDTO;

/**
 *
 */
public interface MeasurementTaskListener {

    void onReceived(Measurement measurement, I2cSensorDTO sensor);
}
