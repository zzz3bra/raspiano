package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.RaspberryService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.RaspberryDTO;
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
 * REST controller for managing Raspberry.
 */
@RestController
@RequestMapping("/api")
public class RaspberryResource {

    private final Logger log = LoggerFactory.getLogger(RaspberryResource.class);

    private static final String ENTITY_NAME = "raspberry";
        
    private final RaspberryService raspberryService;

    public RaspberryResource(RaspberryService raspberryService) {
        this.raspberryService = raspberryService;
    }

    /**
     * POST  /raspberries : Create a new raspberry.
     *
     * @param raspberryDTO the raspberryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raspberryDTO, or with status 400 (Bad Request) if the raspberry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raspberries")
    @Timed
    public ResponseEntity<RaspberryDTO> createRaspberry(@RequestBody RaspberryDTO raspberryDTO) throws URISyntaxException {
        log.debug("REST request to save Raspberry : {}", raspberryDTO);
        if (raspberryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new raspberry cannot already have an ID")).body(null);
        }
        RaspberryDTO result = raspberryService.save(raspberryDTO);
        return ResponseEntity.created(new URI("/api/raspberries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raspberries : Updates an existing raspberry.
     *
     * @param raspberryDTO the raspberryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raspberryDTO,
     * or with status 400 (Bad Request) if the raspberryDTO is not valid,
     * or with status 500 (Internal Server Error) if the raspberryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raspberries")
    @Timed
    public ResponseEntity<RaspberryDTO> updateRaspberry(@RequestBody RaspberryDTO raspberryDTO) throws URISyntaxException {
        log.debug("REST request to update Raspberry : {}", raspberryDTO);
        if (raspberryDTO.getId() == null) {
            return createRaspberry(raspberryDTO);
        }
        RaspberryDTO result = raspberryService.save(raspberryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raspberryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raspberries : get all the raspberries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of raspberries in body
     */
    @GetMapping("/raspberries")
    @Timed
    public List<RaspberryDTO> getAllRaspberries() {
        log.debug("REST request to get all Raspberries");
        return raspberryService.findAll();
    }

    /**
     * GET  /raspberries/:id : get the "id" raspberry.
     *
     * @param id the id of the raspberryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raspberryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/raspberries/{id}")
    @Timed
    public ResponseEntity<RaspberryDTO> getRaspberry(@PathVariable Long id) {
        log.debug("REST request to get Raspberry : {}", id);
        RaspberryDTO raspberryDTO = raspberryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raspberryDTO));
    }

    /**
     * DELETE  /raspberries/:id : delete the "id" raspberry.
     *
     * @param id the id of the raspberryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raspberries/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaspberry(@PathVariable Long id) {
        log.debug("REST request to delete Raspberry : {}", id);
        raspberryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
