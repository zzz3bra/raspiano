package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.MeasurementDTO;
import java.util.List;

/**
 * Service Interface for managing Measurement.
 */
public interface MeasurementService {

    /**
     * Save a measurement.
     *
     * @param measurementDTO the entity to save
     * @return the persisted entity
     */
    MeasurementDTO save(MeasurementDTO measurementDTO);

    /**
     *  Get all the measurements.
     *  
     *  @return the list of entities
     */
    List<MeasurementDTO> findAll();

    /**
     *  Get the "id" measurement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeasurementDTO findOne(Long id);

    /**
     *  Delete the "id" measurement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
