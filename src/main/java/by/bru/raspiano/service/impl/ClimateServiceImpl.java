package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.ClimateService;
import by.bru.raspiano.domain.Climate;
import by.bru.raspiano.repository.ClimateRepository;
import by.bru.raspiano.service.dto.ClimateDTO;
import by.bru.raspiano.service.mapper.ClimateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Climate.
 */
@Service
@Transactional
public class ClimateServiceImpl implements ClimateService{

    private final Logger log = LoggerFactory.getLogger(ClimateServiceImpl.class);
    
    private final ClimateRepository climateRepository;

    private final ClimateMapper climateMapper;

    public ClimateServiceImpl(ClimateRepository climateRepository, ClimateMapper climateMapper) {
        this.climateRepository = climateRepository;
        this.climateMapper = climateMapper;
    }

    /**
     * Save a climate.
     *
     * @param climateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClimateDTO save(ClimateDTO climateDTO) {
        log.debug("Request to save Climate : {}", climateDTO);
        Climate climate = climateMapper.climateDTOToClimate(climateDTO);
        climate = climateRepository.save(climate);
        ClimateDTO result = climateMapper.climateToClimateDTO(climate);
        return result;
    }

    /**
     *  Get all the climates.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClimateDTO> findAll() {
        log.debug("Request to get all Climates");
        List<ClimateDTO> result = climateRepository.findAll().stream()
            .map(climateMapper::climateToClimateDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one climate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClimateDTO findOne(Long id) {
        log.debug("Request to get Climate : {}", id);
        Climate climate = climateRepository.findOne(id);
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);
        return climateDTO;
    }

    /**
     *  Delete the  climate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Climate : {}", id);
        climateRepository.delete(id);
    }
}
