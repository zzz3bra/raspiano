package by.bru.raspiano.service;

import by.bru.raspiano.device.sensor.Measurement;
import by.bru.raspiano.device.sensor.MeasurementTask;
import by.bru.raspiano.device.sensor.sht21.SHT21;
import by.bru.raspiano.device.sensor.sht21.SHT21DummyImpl;
import by.bru.raspiano.domain.enumeration.SensorType;
import by.bru.raspiano.service.dto.I2cDeviceDTO;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import by.bru.raspiano.service.dto.MeasurementDTO;
import by.bru.raspiano.service.dto.RaspberryDTO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.bru.raspiano.device.sensor.sht21.SHT21.DEFAULT_DELAY;
import static by.bru.raspiano.device.sensor.sht21.SHT21.DEFAULT_INTERVAL;

/**
 * Created by Radzivon_Hrechukha on 4/20/2017.
 */

@Service
@Transactional
public class RaspberryCommunicationService {

    private final MeasurementService measurementService;
    private final I2cSensorService i2cSensorService;
    private final I2cDeviceService i2cDeviceService;
    private final RaspberryService raspberryService;

    private final List<I2cSensorDTO> sensors = new ArrayList<>();

    public RaspberryCommunicationService(MeasurementService measurementService, I2cSensorService i2cSensorService,
                                         I2cDeviceService i2cDeviceService, RaspberryService raspberryService) {
        this.measurementService = measurementService;
        this.i2cSensorService = i2cSensorService;
        this.i2cDeviceService = i2cDeviceService;
        this.raspberryService = raspberryService;
    }

    public void addSensors(List<I2cSensorDTO> sensors) {
        this.sensors.addAll(sensors);
    }

    public void startMeasurements() {
        I2cSensorDTO tempSensor = i2cSensorService.findAll().stream()
            .filter(i2cSensorDTO -> i2cSensorDTO.getSensorType().equals(SensorType.TEMPERATURE)).findFirst().get();
        I2cSensorDTO humiditySensor = i2cSensorService.findAll().stream()
            .filter(i2cSensorDTO -> i2cSensorDTO.getSensorType().equals(SensorType.HUMIDITY)).findFirst().get();
        addSensors(Lists.newArrayList(tempSensor, humiditySensor));

        SHT21 sht21 = SHT21DummyImpl.create(0, SHT21.I2C_ADDRESS, sensors);
//            if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT, JHipsterConstants.SPRING_PROFILE_TEST)) {
//                sht21 = new SHT21DummyImpl();
//            } else if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
//                sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);
//            } else {
//                throw new RuntimeException("Please start application with some profile in order to work with sensors");
//            }
        MeasurementTask measurementTask = MeasurementTask.create(sht21, DEFAULT_DELAY, DEFAULT_INTERVAL);
        measurementTask.addListener(this::splitAndSaveMeasurement);
    }

    private void splitAndSaveMeasurement(Measurement measurement, I2cSensorDTO sensor) {
        I2cDeviceDTO deviceDTO = i2cDeviceService.findAll().stream()
            .filter(i2cDeviceDTO -> i2cDeviceDTO.getDeviceId().equals(sensor.getDeviceId())).findFirst().get();
        RaspberryDTO raspberryDTO = raspberryService.findAll().stream()
            .filter(raspberryDTO1 -> raspberryDTO1.getId().equals(deviceDTO.getDeviceId())).findFirst().get();

        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setClimateId(raspberryDTO.getClimateId());
        measurementDTO.setDateTime(ZonedDateTime.ofInstant(measurement.getCreatedAt().toInstant(), ZoneId.systemDefault()));
        measurementDTO.setValue(Math.round(measurement.getValue()));
        measurementDTO.setSourceId(sensor.getId());

        measurementService.save(measurementDTO);
    }
}
