package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.I2cDeviceDTO;
import java.util.List;

/**
 * Service Interface for managing I2cDevice.
 */
public interface I2cDeviceService {

    /**
     * Save a i2cDevice.
     *
     * @param i2cDeviceDTO the entity to save
     * @return the persisted entity
     */
    I2cDeviceDTO save(I2cDeviceDTO i2cDeviceDTO);

    /**
     *  Get all the i2cDevices.
     *  
     *  @return the list of entities
     */
    List<I2cDeviceDTO> findAll();

    /**
     *  Get the "id" i2cDevice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    I2cDeviceDTO findOne(Long id);

    /**
     *  Delete the "id" i2cDevice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
