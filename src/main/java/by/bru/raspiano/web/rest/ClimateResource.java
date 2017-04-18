package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.ClimateService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.ClimateDTO;
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
 * REST controller for managing Climate.
 */
@RestController
@RequestMapping("/api")
public class ClimateResource {

    private final Logger log = LoggerFactory.getLogger(ClimateResource.class);

    private static final String ENTITY_NAME = "climate";
        
    private final ClimateService climateService;

    public ClimateResource(ClimateService climateService) {
        this.climateService = climateService;
    }

    /**
     * POST  /climates : Create a new climate.
     *
     * @param climateDTO the climateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new climateDTO, or with status 400 (Bad Request) if the climate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/climates")
    @Timed
    public ResponseEntity<ClimateDTO> createClimate(@Valid @RequestBody ClimateDTO climateDTO) throws URISyntaxException {
        log.debug("REST request to save Climate : {}", climateDTO);
        if (climateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new climate cannot already have an ID")).body(null);
        }
        ClimateDTO result = climateService.save(climateDTO);
        return ResponseEntity.created(new URI("/api/climates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /climates : Updates an existing climate.
     *
     * @param climateDTO the climateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated climateDTO,
     * or with status 400 (Bad Request) if the climateDTO is not valid,
     * or with status 500 (Internal Server Error) if the climateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/climates")
    @Timed
    public ResponseEntity<ClimateDTO> updateClimate(@Valid @RequestBody ClimateDTO climateDTO) throws URISyntaxException {
        log.debug("REST request to update Climate : {}", climateDTO);
        if (climateDTO.getId() == null) {
            return createClimate(climateDTO);
        }
        ClimateDTO result = climateService.save(climateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, climateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /climates : get all the climates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of climates in body
     */
    @GetMapping("/climates")
    @Timed
    public List<ClimateDTO> getAllClimates() {
        log.debug("REST request to get all Climates");
        return climateService.findAll();
    }

    /**
     * GET  /climates/:id : get the "id" climate.
     *
     * @param id the id of the climateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the climateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/climates/{id}")
    @Timed
    public ResponseEntity<ClimateDTO> getClimate(@PathVariable Long id) {
        log.debug("REST request to get Climate : {}", id);
        ClimateDTO climateDTO = climateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(climateDTO));
    }

    /**
     * DELETE  /climates/:id : delete the "id" climate.
     *
     * @param id the id of the climateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/climates/{id}")
    @Timed
    public ResponseEntity<Void> deleteClimate(@PathVariable Long id) {
        log.debug("REST request to delete Climate : {}", id);
        climateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
