package by.bru.raspiano.service;

import by.bru.raspiano.device.sensor.Measurement;
import by.bru.raspiano.device.sensor.MeasurementTask;
import by.bru.raspiano.device.sensor.sht21.SHT21;
import by.bru.raspiano.device.sensor.sht21.SHT21DummyImpl;
import by.bru.raspiano.service.dto.ClimateDTO;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import by.bru.raspiano.service.dto.MeasurementDTO;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static by.bru.raspiano.device.sensor.sht21.SHT21.DEFAULT_DELAY;
import static by.bru.raspiano.device.sensor.sht21.SHT21.DEFAULT_INTERVAL;

/**
 * Created by Radzivon_Hrechukha on 4/20/2017.
 */

@Service
public class RaspberryCommunicationService {

    private final MeasurementService measurementService;
    private final ClimateService climateService;

    private final Environment env;

    private final ArrayList<I2cSensorDTO> sensors = new ArrayList<>();

    public RaspberryCommunicationService(MeasurementService measurementService, ClimateService climateService, Environment env) {
        this.measurementService = measurementService;
        this.climateService = climateService;
        this.env = env;
    }

    public void addSensors(I2cSensorDTO... sensors) {
        this.sensors.addAll(Arrays.asList(sensors));
    }

    public void startMeasurements() {
        for (final I2cSensorDTO sensor : sensors) {
            SHT21 sht21 = new SHT21DummyImpl();
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
    }

    private void splitAndSaveMeasurement(Measurement measurement) {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setClimateId(climateService.findAll().stream().findFirst().orElse(new ClimateDTO()).getId());
        measurementDTO.setDateTime(ZonedDateTime.ofInstant(measurement.getCreatedAt().toInstant(), ZoneId.systemDefault()));
        measurementDTO.setValue(Math.round(measurement.getValue()));
        switch (measurement.getType()) {
            //TODO: hardcoded  values of sensors from database
            case TEMPERATURE:
                measurementDTO.setSourceId(1L);
                break;
            case HUMIDITY:
                measurementDTO.setSourceId(2L);
                break;
            default:
                throw new RuntimeException("Unsupported measurement type get :" + measurement.getType().toString());
        }
        measurementService.save(measurementDTO);
    }
}
