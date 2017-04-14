package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.ControllerActonService;
import by.bru.raspiano.domain.ControllerActon;
import by.bru.raspiano.repository.ControllerActonRepository;
import by.bru.raspiano.service.dto.ControllerActonDTO;
import by.bru.raspiano.service.mapper.ControllerActonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ControllerActon.
 */
@Service
@Transactional
public class ControllerActonServiceImpl implements ControllerActonService{

    private final Logger log = LoggerFactory.getLogger(ControllerActonServiceImpl.class);
    
    private final ControllerActonRepository controllerActonRepository;

    private final ControllerActonMapper controllerActonMapper;

    public ControllerActonServiceImpl(ControllerActonRepository controllerActonRepository, ControllerActonMapper controllerActonMapper) {
        this.controllerActonRepository = controllerActonRepository;
        this.controllerActonMapper = controllerActonMapper;
    }

    /**
     * Save a controllerActon.
     *
     * @param controllerActonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ControllerActonDTO save(ControllerActonDTO controllerActonDTO) {
        log.debug("Request to save ControllerActon : {}", controllerActonDTO);
        ControllerActon controllerActon = controllerActonMapper.controllerActonDTOToControllerActon(controllerActonDTO);
        controllerActon = controllerActonRepository.save(controllerActon);
        ControllerActonDTO result = controllerActonMapper.controllerActonToControllerActonDTO(controllerActon);
        return result;
    }

    /**
     *  Get all the controllerActons.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ControllerActonDTO> findAll() {
        log.debug("Request to get all ControllerActons");
        List<ControllerActonDTO> result = controllerActonRepository.findAll().stream()
            .map(controllerActonMapper::controllerActonToControllerActonDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one controllerActon by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ControllerActonDTO findOne(Long id) {
        log.debug("Request to get ControllerActon : {}", id);
        ControllerActon controllerActon = controllerActonRepository.findOne(id);
        ControllerActonDTO controllerActonDTO = controllerActonMapper.controllerActonToControllerActonDTO(controllerActon);
        return controllerActonDTO;
    }

    /**
     *  Delete the  controllerActon by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ControllerActon : {}", id);
        controllerActonRepository.delete(id);
    }
}
