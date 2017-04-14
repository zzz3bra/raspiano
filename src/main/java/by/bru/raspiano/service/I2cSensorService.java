package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.I2cSensorDTO;
import java.util.List;

/**
 * Service Interface for managing I2cSensor.
 */
public interface I2cSensorService {

    /**
     * Save a i2cSensor.
     *
     * @param i2cSensorDTO the entity to save
     * @return the persisted entity
     */
    I2cSensorDTO save(I2cSensorDTO i2cSensorDTO);

    /**
     *  Get all the i2cSensors.
     *  
     *  @return the list of entities
     */
    List<I2cSensorDTO> findAll();

    /**
     *  Get the "id" i2cSensor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    I2cSensorDTO findOne(Long id);

    /**
     *  Delete the "id" i2cSensor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
