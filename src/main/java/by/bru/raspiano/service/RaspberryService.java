package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.RaspberryDTO;
import java.util.List;

/**
 * Service Interface for managing Raspberry.
 */
public interface RaspberryService {

    /**
     * Save a raspberry.
     *
     * @param raspberryDTO the entity to save
     * @return the persisted entity
     */
    RaspberryDTO save(RaspberryDTO raspberryDTO);

    /**
     *  Get all the raspberries.
     *  
     *  @return the list of entities
     */
    List<RaspberryDTO> findAll();

    /**
     *  Get the "id" raspberry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RaspberryDTO findOne(Long id);

    /**
     *  Delete the "id" raspberry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
