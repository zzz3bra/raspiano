package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.I2cControllerService;
import by.bru.raspiano.domain.I2cController;
import by.bru.raspiano.repository.I2cControllerRepository;
import by.bru.raspiano.service.dto.I2cControllerDTO;
import by.bru.raspiano.service.mapper.I2cControllerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing I2cController.
 */
@Service
@Transactional
public class I2cControllerServiceImpl implements I2cControllerService{

    private final Logger log = LoggerFactory.getLogger(I2cControllerServiceImpl.class);
    
    private final I2cControllerRepository i2cControllerRepository;

    private final I2cControllerMapper i2cControllerMapper;

    public I2cControllerServiceImpl(I2cControllerRepository i2cControllerRepository, I2cControllerMapper i2cControllerMapper) {
        this.i2cControllerRepository = i2cControllerRepository;
        this.i2cControllerMapper = i2cControllerMapper;
    }

    /**
     * Save a i2cController.
     *
     * @param i2cControllerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public I2cControllerDTO save(I2cControllerDTO i2cControllerDTO) {
        log.debug("Request to save I2cController : {}", i2cControllerDTO);
        I2cController i2cController = i2cControllerMapper.i2cControllerDTOToI2cController(i2cControllerDTO);
        i2cController = i2cControllerRepository.save(i2cController);
        I2cControllerDTO result = i2cControllerMapper.i2cControllerToI2cControllerDTO(i2cController);
        return result;
    }

    /**
     *  Get all the i2cControllers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<I2cControllerDTO> findAll() {
        log.debug("Request to get all I2cControllers");
        List<I2cControllerDTO> result = i2cControllerRepository.findAll().stream()
            .map(i2cControllerMapper::i2cControllerToI2cControllerDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one i2cController by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public I2cControllerDTO findOne(Long id) {
        log.debug("Request to get I2cController : {}", id);
        I2cController i2cController = i2cControllerRepository.findOne(id);
        I2cControllerDTO i2cControllerDTO = i2cControllerMapper.i2cControllerToI2cControllerDTO(i2cController);
        return i2cControllerDTO;
    }

    /**
     *  Delete the  i2cController by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete I2cController : {}", id);
        i2cControllerRepository.delete(id);
    }
}
