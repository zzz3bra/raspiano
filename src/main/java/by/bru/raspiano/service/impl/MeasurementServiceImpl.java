package by.bru.raspiano.service.impl;

import by.bru.raspiano.service.MeasurementService;
import by.bru.raspiano.domain.Measurement;
import by.bru.raspiano.repository.MeasurementRepository;
import by.bru.raspiano.service.dto.MeasurementDTO;
import by.bru.raspiano.service.mapper.MeasurementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Measurement.
 */
@Service
@Transactional
public class MeasurementServiceImpl implements MeasurementService{

    private final Logger log = LoggerFactory.getLogger(MeasurementServiceImpl.class);

    private final MeasurementRepository measurementRepository;

    private final MeasurementMapper measurementMapper;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
    }

    /**
     * Save a measurement.
     *
     * @param measurementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MeasurementDTO save(MeasurementDTO measurementDTO) {
        log.debug("Request to save Measurement : {}", measurementDTO);
        Measurement measurement = measurementMapper.measurementDTOToMeasurement(measurementDTO);
        measurement = measurementRepository.save(measurement);
        MeasurementDTO result = measurementMapper.measurementToMeasurementDTO(measurement);
        return result;
    }

    /**
     *  Get all the measurements.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeasurementDTO> findAll(LocalDateTime fromDate, LocalDateTime toDate) {
        log.debug("Request to get all Measurements");
        List<MeasurementDTO> result = measurementRepository
            .findAllByDateTimeBetween(ZonedDateTime.of(fromDate, ZoneId.systemDefault()), ZonedDateTime.of(toDate, ZoneId.systemDefault()))
            .stream()
            .map(measurementMapper::measurementToMeasurementDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one measurement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeasurementDTO findOne(Long id) {
        log.debug("Request to get Measurement : {}", id);
        Measurement measurement = measurementRepository.findOne(id);
        MeasurementDTO measurementDTO = measurementMapper.measurementToMeasurementDTO(measurement);
        return measurementDTO;
    }

    /**
     *  Delete the  measurement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Measurement : {}", id);
        measurementRepository.delete(id);
    }
}
