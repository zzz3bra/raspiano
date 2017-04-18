package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.I2cSensorService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing I2cSensor.
 */
@RestController
@RequestMapping("/api")
public class I2cSensorResource {

    private final Logger log = LoggerFactory.getLogger(I2cSensorResource.class);

    private static final String ENTITY_NAME = "i2cSensor";
        
    private final I2cSensorService i2cSensorService;

    public I2cSensorResource(I2cSensorService i2cSensorService) {
        this.i2cSensorService = i2cSensorService;
    }

    /**
     * POST  /i-2-c-sensors : Create a new i2cSensor.
     *
     * @param i2cSensorDTO the i2cSensorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new i2cSensorDTO, or with status 400 (Bad Request) if the i2cSensor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/i-2-c-sensors")
    @Timed
    public ResponseEntity<I2cSensorDTO> createI2cSensor(@Valid @RequestBody I2cSensorDTO i2cSensorDTO) throws URISyntaxException {
        log.debug("REST request to save I2cSensor : {}", i2cSensorDTO);
        if (i2cSensorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new i2cSensor cannot already have an ID")).body(null);
        }
        I2cSensorDTO result = i2cSensorService.save(i2cSensorDTO);
        return ResponseEntity.created(new URI("/api/i-2-c-sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /i-2-c-sensors : Updates an existing i2cSensor.
     *
     * @param i2cSensorDTO the i2cSensorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated i2cSensorDTO,
     * or with status 400 (Bad Request) if the i2cSensorDTO is not valid,
     * or with status 500 (Internal Server Error) if the i2cSensorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/i-2-c-sensors")
    @Timed
    public ResponseEntity<I2cSensorDTO> updateI2cSensor(@Valid @RequestBody I2cSensorDTO i2cSensorDTO) throws URISyntaxException {
        log.debug("REST request to update I2cSensor : {}", i2cSensorDTO);
        if (i2cSensorDTO.getId() == null) {
            return createI2cSensor(i2cSensorDTO);
        }
        I2cSensorDTO result = i2cSensorService.save(i2cSensorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, i2cSensorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /i-2-c-sensors : get all the i2cSensors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of i2cSensors in body
     */
    @GetMapping("/i-2-c-sensors")
    @Timed
    public List<I2cSensorDTO> getAllI2cSensors() {
        log.debug("REST request to get all I2cSensors");
        return i2cSensorService.findAll();
    }

    /**
     * GET  /i-2-c-sensors/:id : get the "id" i2cSensor.
     *
     * @param id the id of the i2cSensorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the i2cSensorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/i-2-c-sensors/{id}")
    @Timed
    public ResponseEntity<I2cSensorDTO> getI2cSensor(@PathVariable Long id) {
        log.debug("REST request to get I2cSensor : {}", id);
        I2cSensorDTO i2cSensorDTO = i2cSensorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(i2cSensorDTO));
    }

    /**
     * DELETE  /i-2-c-sensors/:id : delete the "id" i2cSensor.
     *
     * @param id the id of the i2cSensorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/i-2-c-sensors/{id}")
    @Timed
    public ResponseEntity<Void> deleteI2cSensor(@PathVariable Long id) {
        log.debug("REST request to delete I2cSensor : {}", id);
        i2cSensorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
