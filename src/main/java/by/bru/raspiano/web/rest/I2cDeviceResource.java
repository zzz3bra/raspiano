package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.I2cDeviceService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.I2cDeviceDTO;
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
 * REST controller for managing I2cDevice.
 */
@RestController
@RequestMapping("/api")
public class I2cDeviceResource {

    private final Logger log = LoggerFactory.getLogger(I2cDeviceResource.class);

    private static final String ENTITY_NAME = "i2cDevice";
        
    private final I2cDeviceService i2cDeviceService;

    public I2cDeviceResource(I2cDeviceService i2cDeviceService) {
        this.i2cDeviceService = i2cDeviceService;
    }

    /**
     * POST  /i-2-c-devices : Create a new i2cDevice.
     *
     * @param i2cDeviceDTO the i2cDeviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new i2cDeviceDTO, or with status 400 (Bad Request) if the i2cDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/i-2-c-devices")
    @Timed
    public ResponseEntity<I2cDeviceDTO> createI2cDevice(@Valid @RequestBody I2cDeviceDTO i2cDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save I2cDevice : {}", i2cDeviceDTO);
        if (i2cDeviceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new i2cDevice cannot already have an ID")).body(null);
        }
        I2cDeviceDTO result = i2cDeviceService.save(i2cDeviceDTO);
        return ResponseEntity.created(new URI("/api/i-2-c-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /i-2-c-devices : Updates an existing i2cDevice.
     *
     * @param i2cDeviceDTO the i2cDeviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated i2cDeviceDTO,
     * or with status 400 (Bad Request) if the i2cDeviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the i2cDeviceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/i-2-c-devices")
    @Timed
    public ResponseEntity<I2cDeviceDTO> updateI2cDevice(@Valid @RequestBody I2cDeviceDTO i2cDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update I2cDevice : {}", i2cDeviceDTO);
        if (i2cDeviceDTO.getId() == null) {
            return createI2cDevice(i2cDeviceDTO);
        }
        I2cDeviceDTO result = i2cDeviceService.save(i2cDeviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, i2cDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /i-2-c-devices : get all the i2cDevices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of i2cDevices in body
     */
    @GetMapping("/i-2-c-devices")
    @Timed
    public List<I2cDeviceDTO> getAllI2cDevices() {
        log.debug("REST request to get all I2cDevices");
        return i2cDeviceService.findAll();
    }

    /**
     * GET  /i-2-c-devices/:id : get the "id" i2cDevice.
     *
     * @param id the id of the i2cDeviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the i2cDeviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/i-2-c-devices/{id}")
    @Timed
    public ResponseEntity<I2cDeviceDTO> getI2cDevice(@PathVariable Long id) {
        log.debug("REST request to get I2cDevice : {}", id);
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(i2cDeviceDTO));
    }

    /**
     * DELETE  /i-2-c-devices/:id : delete the "id" i2cDevice.
     *
     * @param id the id of the i2cDeviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/i-2-c-devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteI2cDevice(@PathVariable Long id) {
        log.debug("REST request to delete I2cDevice : {}", id);
        i2cDeviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
