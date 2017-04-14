package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.ClimateDTO;
import java.util.List;

/**
 * Service Interface for managing Climate.
 */
public interface ClimateService {

    /**
     * Save a climate.
     *
     * @param climateDTO the entity to save
     * @return the persisted entity
     */
    ClimateDTO save(ClimateDTO climateDTO);

    /**
     *  Get all the climates.
     *  
     *  @return the list of entities
     */
    List<ClimateDTO> findAll();

    /**
     *  Get the "id" climate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClimateDTO findOne(Long id);

    /**
     *  Delete the "id" climate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
