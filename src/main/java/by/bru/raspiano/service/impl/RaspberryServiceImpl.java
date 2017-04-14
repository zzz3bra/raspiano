package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.RaspberryService;
import by.bru.raspiano.domain.Raspberry;
import by.bru.raspiano.repository.RaspberryRepository;
import by.bru.raspiano.service.dto.RaspberryDTO;
import by.bru.raspiano.service.mapper.RaspberryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Raspberry.
 */
@Service
@Transactional
public class RaspberryServiceImpl implements RaspberryService{

    private final Logger log = LoggerFactory.getLogger(RaspberryServiceImpl.class);
    
    private final RaspberryRepository raspberryRepository;

    private final RaspberryMapper raspberryMapper;

    public RaspberryServiceImpl(RaspberryRepository raspberryRepository, RaspberryMapper raspberryMapper) {
        this.raspberryRepository = raspberryRepository;
        this.raspberryMapper = raspberryMapper;
    }

    /**
     * Save a raspberry.
     *
     * @param raspberryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RaspberryDTO save(RaspberryDTO raspberryDTO) {
        log.debug("Request to save Raspberry : {}", raspberryDTO);
        Raspberry raspberry = raspberryMapper.raspberryDTOToRaspberry(raspberryDTO);
        raspberry = raspberryRepository.save(raspberry);
        RaspberryDTO result = raspberryMapper.raspberryToRaspberryDTO(raspberry);
        return result;
    }

    /**
     *  Get all the raspberries.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RaspberryDTO> findAll() {
        log.debug("Request to get all Raspberries");
        List<RaspberryDTO> result = raspberryRepository.findAll().stream()
            .map(raspberryMapper::raspberryToRaspberryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one raspberry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RaspberryDTO findOne(Long id) {
        log.debug("Request to get Raspberry : {}", id);
        Raspberry raspberry = raspberryRepository.findOne(id);
        RaspberryDTO raspberryDTO = raspberryMapper.raspberryToRaspberryDTO(raspberry);
        return raspberryDTO;
    }

    /**
     *  Delete the  raspberry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Raspberry : {}", id);
        raspberryRepository.delete(id);
    }
}
