package by.bru.raspiano.service;

import by.bru.raspiano.service.dto.ControllerActonDTO;
import java.util.List;

/**
 * Service Interface for managing ControllerActon.
 */
public interface ControllerActonService {

    /**
     * Save a controllerActon.
     *
     * @param controllerActonDTO the entity to save
     * @return the persisted entity
     */
    ControllerActonDTO save(ControllerActonDTO controllerActonDTO);

    /**
     *  Get all the controllerActons.
     *  
     *  @return the list of entities
     */
    List<ControllerActonDTO> findAll();

    /**
     *  Get the "id" controllerActon.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ControllerActonDTO findOne(Long id);

    /**
     *  Delete the "id" controllerActon.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
