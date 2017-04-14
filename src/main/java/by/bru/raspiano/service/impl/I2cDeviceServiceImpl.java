package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.I2cDeviceService;
import by.bru.raspiano.domain.I2cDevice;
import by.bru.raspiano.repository.I2cDeviceRepository;
import by.bru.raspiano.service.dto.I2cDeviceDTO;
import by.bru.raspiano.service.mapper.I2cDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing I2cDevice.
 */
@Service
@Transactional
public class I2cDeviceServiceImpl implements I2cDeviceService{

    private final Logger log = LoggerFactory.getLogger(I2cDeviceServiceImpl.class);
    
    private final I2cDeviceRepository i2cDeviceRepository;

    private final I2cDeviceMapper i2cDeviceMapper;

    public I2cDeviceServiceImpl(I2cDeviceRepository i2cDeviceRepository, I2cDeviceMapper i2cDeviceMapper) {
        this.i2cDeviceRepository = i2cDeviceRepository;
        this.i2cDeviceMapper = i2cDeviceMapper;
    }

    /**
     * Save a i2cDevice.
     *
     * @param i2cDeviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public I2cDeviceDTO save(I2cDeviceDTO i2cDeviceDTO) {
        log.debug("Request to save I2cDevice : {}", i2cDeviceDTO);
        I2cDevice i2cDevice = i2cDeviceMapper.i2cDeviceDTOToI2cDevice(i2cDeviceDTO);
        i2cDevice = i2cDeviceRepository.save(i2cDevice);
        I2cDeviceDTO result = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);
        return result;
    }

    /**
     *  Get all the i2cDevices.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<I2cDeviceDTO> findAll() {
        log.debug("Request to get all I2cDevices");
        List<I2cDeviceDTO> result = i2cDeviceRepository.findAll().stream()
            .map(i2cDeviceMapper::i2cDeviceToI2cDeviceDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one i2cDevice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public I2cDeviceDTO findOne(Long id) {
        log.debug("Request to get I2cDevice : {}", id);
        I2cDevice i2cDevice = i2cDeviceRepository.findOne(id);
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);
        return i2cDeviceDTO;
    }

    /**
     *  Delete the  i2cDevice by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete I2cDevice : {}", id);
        i2cDeviceRepository.delete(id);
    }
}
