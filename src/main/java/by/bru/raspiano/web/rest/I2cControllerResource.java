package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.I2cControllerService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.I2cControllerDTO;
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
 * REST controller for managing I2cController.
 */
@RestController
@RequestMapping("/api")
public class I2cControllerResource {

    private final Logger log = LoggerFactory.getLogger(I2cControllerResource.class);

    private static final String ENTITY_NAME = "i2cController";
        
    private final I2cControllerService i2cControllerService;

    public I2cControllerResource(I2cControllerService i2cControllerService) {
        this.i2cControllerService = i2cControllerService;
    }

    /**
     * POST  /i-2-c-controllers : Create a new i2cController.
     *
     * @param i2cControllerDTO the i2cControllerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new i2cControllerDTO, or with status 400 (Bad Request) if the i2cController has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/i-2-c-controllers")
    @Timed
    public ResponseEntity<I2cControllerDTO> createI2cController(@RequestBody I2cControllerDTO i2cControllerDTO) throws URISyntaxException {
        log.debug("REST request to save I2cController : {}", i2cControllerDTO);
        if (i2cControllerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new i2cController cannot already have an ID")).body(null);
        }
        I2cControllerDTO result = i2cControllerService.save(i2cControllerDTO);
        return ResponseEntity.created(new URI("/api/i-2-c-controllers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /i-2-c-controllers : Updates an existing i2cController.
     *
     * @param i2cControllerDTO the i2cControllerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated i2cControllerDTO,
     * or with status 400 (Bad Request) if the i2cControllerDTO is not valid,
     * or with status 500 (Internal Server Error) if the i2cControllerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/i-2-c-controllers")
    @Timed
    public ResponseEntity<I2cControllerDTO> updateI2cController(@RequestBody I2cControllerDTO i2cControllerDTO) throws URISyntaxException {
        log.debug("REST request to update I2cController : {}", i2cControllerDTO);
        if (i2cControllerDTO.getId() == null) {
            return createI2cController(i2cControllerDTO);
        }
        I2cControllerDTO result = i2cControllerService.save(i2cControllerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, i2cControllerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /i-2-c-controllers : get all the i2cControllers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of i2cControllers in body
     */
    @GetMapping("/i-2-c-controllers")
    @Timed
    public List<I2cControllerDTO> getAllI2cControllers() {
        log.debug("REST request to get all I2cControllers");
        return i2cControllerService.findAll();
    }

    /**
     * GET  /i-2-c-controllers/:id : get the "id" i2cController.
     *
     * @param id the id of the i2cControllerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the i2cControllerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/i-2-c-controllers/{id}")
    @Timed
    public ResponseEntity<I2cControllerDTO> getI2cController(@PathVariable Long id) {
        log.debug("REST request to get I2cController : {}", id);
        I2cControllerDTO i2cControllerDTO = i2cControllerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(i2cControllerDTO));
    }

    /**
     * DELETE  /i-2-c-controllers/:id : delete the "id" i2cController.
     *
     * @param id the id of the i2cControllerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/i-2-c-controllers/{id}")
    @Timed
    public ResponseEntity<Void> deleteI2cController(@PathVariable Long id) {
        log.debug("REST request to delete I2cController : {}", id);
        i2cControllerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
