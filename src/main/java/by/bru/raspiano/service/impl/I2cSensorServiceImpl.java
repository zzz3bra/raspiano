package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.I2cSensorService;
import by.bru.raspiano.domain.I2cSensor;
import by.bru.raspiano.repository.I2cSensorRepository;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import by.bru.raspiano.service.mapper.I2cSensorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing I2cSensor.
 */
@Service
@Transactional
public class I2cSensorServiceImpl implements I2cSensorService{

    private final Logger log = LoggerFactory.getLogger(I2cSensorServiceImpl.class);
    
    private final I2cSensorRepository i2cSensorRepository;

    private final I2cSensorMapper i2cSensorMapper;

    public I2cSensorServiceImpl(I2cSensorRepository i2cSensorRepository, I2cSensorMapper i2cSensorMapper) {
        this.i2cSensorRepository = i2cSensorRepository;
        this.i2cSensorMapper = i2cSensorMapper;
    }

    /**
     * Save a i2cSensor.
     *
     * @param i2cSensorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public I2cSensorDTO save(I2cSensorDTO i2cSensorDTO) {
        log.debug("Request to save I2cSensor : {}", i2cSensorDTO);
        I2cSensor i2cSensor = i2cSensorMapper.i2cSensorDTOToI2cSensor(i2cSensorDTO);
        i2cSensor = i2cSensorRepository.save(i2cSensor);
        I2cSensorDTO result = i2cSensorMapper.i2cSensorToI2cSensorDTO(i2cSensor);
        return result;
    }

    /**
     *  Get all the i2cSensors.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<I2cSensorDTO> findAll() {
        log.debug("Request to get all I2cSensors");
        List<I2cSensorDTO> result = i2cSensorRepository.findAll().stream()
            .map(i2cSensorMapper::i2cSensorToI2cSensorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one i2cSensor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public I2cSensorDTO findOne(Long id) {
        log.debug("Request to get I2cSensor : {}", id);
        I2cSensor i2cSensor = i2cSensorRepository.findOne(id);
        I2cSensorDTO i2cSensorDTO = i2cSensorMapper.i2cSensorToI2cSensorDTO(i2cSensor);
        return i2cSensorDTO;
    }

    /**
     *  Delete the  i2cSensor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete I2cSensor : {}", id);
        i2cSensorRepository.delete(id);
    }
}
