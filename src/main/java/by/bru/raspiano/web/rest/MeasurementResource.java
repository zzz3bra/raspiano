package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.MeasurementService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.MeasurementDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Measurement.
 */
@RestController
@RequestMapping("/api")
public class MeasurementResource {

    private final Logger log = LoggerFactory.getLogger(MeasurementResource.class);

    private static final String ENTITY_NAME = "measurement";
        
    private final MeasurementService measurementService;

    public MeasurementResource(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    /**
     * POST  /measurements : Create a new measurement.
     *
     * @param measurementDTO the measurementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measurementDTO, or with status 400 (Bad Request) if the measurement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/measurements")
    @Timed
    public ResponseEntity<MeasurementDTO> createMeasurement(@RequestBody MeasurementDTO measurementDTO) throws URISyntaxException {
        log.debug("REST request to save Measurement : {}", measurementDTO);
        if (measurementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new measurement cannot already have an ID")).body(null);
        }
        MeasurementDTO result = measurementService.save(measurementDTO);
        return ResponseEntity.created(new URI("/api/measurements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measurements : Updates an existing measurement.
     *
     * @param measurementDTO the measurementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measurementDTO,
     * or with status 400 (Bad Request) if the measurementDTO is not valid,
     * or with status 500 (Internal Server Error) if the measurementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/measurements")
    @Timed
    public ResponseEntity<MeasurementDTO> updateMeasurement(@RequestBody MeasurementDTO measurementDTO) throws URISyntaxException {
        log.debug("REST request to update Measurement : {}", measurementDTO);
        if (measurementDTO.getId() == null) {
            return createMeasurement(measurementDTO);
        }
        MeasurementDTO result = measurementService.save(measurementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, measurementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measurements : get all the measurements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of measurements in body
     */
    @GetMapping("/measurements")
    @Timed
    public List<MeasurementDTO> getAllMeasurements() {
        log.debug("REST request to get all Measurements");
        return measurementService.findAll();
    }

    /**
     * GET  /measurements/:id : get the "id" measurement.
     *
     * @param id the id of the measurementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measurementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/measurements/{id}")
    @Timed
    public ResponseEntity<MeasurementDTO> getMeasurement(@PathVariable Long id) {
        log.debug("REST request to get Measurement : {}", id);
        MeasurementDTO measurementDTO = measurementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(measurementDTO));
    }

    /**
     * DELETE  /measurements/:id : delete the "id" measurement.
     *
     * @param id the id of the measurementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/measurements/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        log.debug("REST request to delete Measurement : {}", id);
        measurementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
