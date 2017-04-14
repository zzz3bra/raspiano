package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.I2cControllerDTO;
import java.util.List;

/**
 * Service Interface for managing I2cController.
 */
public interface I2cControllerService {

    /**
     * Save a i2cController.
     *
     * @param i2cControllerDTO the entity to save
     * @return the persisted entity
     */
    I2cControllerDTO save(I2cControllerDTO i2cControllerDTO);

    /**
     *  Get all the i2cControllers.
     *  
     *  @return the list of entities
     */
    List<I2cControllerDTO> findAll();

    /**
     *  Get the "id" i2cController.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    I2cControllerDTO findOne(Long id);

    /**
     *  Delete the "id" i2cController.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
