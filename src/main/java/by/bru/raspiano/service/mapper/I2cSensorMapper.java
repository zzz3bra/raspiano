package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.I2cSensorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity I2cSensor and its DTO I2cSensorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface I2cSensorMapper {

    I2cSensorDTO i2cSensorToI2cSensorDTO(I2cSensor i2cSensor);

    List<I2cSensorDTO> i2cSensorsToI2cSensorDTOs(List<I2cSensor> i2cSensors);

    I2cSensor i2cSensorDTOToI2cSensor(I2cSensorDTO i2cSensorDTO);

    List<I2cSensor> i2cSensorDTOsToI2cSensors(List<I2cSensorDTO> i2cSensorDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default I2cSensor i2cSensorFromId(Long id) {
        if (id == null) {
            return null;
        }
        I2cSensor i2cSensor = new I2cSensor();
        i2cSensor.setId(id);
        return i2cSensor;
    }
    

}
