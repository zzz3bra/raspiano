package by.bru.raspiano.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.bru.raspiano.service.ControllerActonService;
import by.bru.raspiano.web.rest.util.HeaderUtil;
import by.bru.raspiano.service.dto.ControllerActonDTO;
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
 * REST controller for managing ControllerActon.
 */
@RestController
@RequestMapping("/api")
public class ControllerActonResource {

    private final Logger log = LoggerFactory.getLogger(ControllerActonResource.class);

    private static final String ENTITY_NAME = "controllerActon";
        
    private final ControllerActonService controllerActonService;

    public ControllerActonResource(ControllerActonService controllerActonService) {
        this.controllerActonService = controllerActonService;
    }

    /**
     * POST  /controller-actons : Create a new controllerActon.
     *
     * @param controllerActonDTO the controllerActonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controllerActonDTO, or with status 400 (Bad Request) if the controllerActon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/controller-actons")
    @Timed
    public ResponseEntity<ControllerActonDTO> createControllerActon(@Valid @RequestBody ControllerActonDTO controllerActonDTO) throws URISyntaxException {
        log.debug("REST request to save ControllerActon : {}", controllerActonDTO);
        if (controllerActonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new controllerActon cannot already have an ID")).body(null);
        }
        ControllerActonDTO result = controllerActonService.save(controllerActonDTO);
        return ResponseEntity.created(new URI("/api/controller-actons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /controller-actons : Updates an existing controllerActon.
     *
     * @param controllerActonDTO the controllerActonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controllerActonDTO,
     * or with status 400 (Bad Request) if the controllerActonDTO is not valid,
     * or with status 500 (Internal Server Error) if the controllerActonDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/controller-actons")
    @Timed
    public ResponseEntity<ControllerActonDTO> updateControllerActon(@Valid @RequestBody ControllerActonDTO controllerActonDTO) throws URISyntaxException {
        log.debug("REST request to update ControllerActon : {}", controllerActonDTO);
        if (controllerActonDTO.getId() == null) {
            return createControllerActon(controllerActonDTO);
        }
        ControllerActonDTO result = controllerActonService.save(controllerActonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controllerActonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /controller-actons : get all the controllerActons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of controllerActons in body
     */
    @GetMapping("/controller-actons")
    @Timed
    public List<ControllerActonDTO> getAllControllerActons() {
        log.debug("REST request to get all ControllerActons");
        return controllerActonService.findAll();
    }

    /**
     * GET  /controller-actons/:id : get the "id" controllerActon.
     *
     * @param id the id of the controllerActonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controllerActonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/controller-actons/{id}")
    @Timed
    public ResponseEntity<ControllerActonDTO> getControllerActon(@PathVariable Long id) {
        log.debug("REST request to get ControllerActon : {}", id);
        ControllerActonDTO controllerActonDTO = controllerActonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(controllerActonDTO));
    }

    /**
     * DELETE  /controller-actons/:id : delete the "id" controllerActon.
     *
     * @param id the id of the controllerActonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/controller-actons/{id}")
    @Timed
    public ResponseEntity<Void> deleteControllerActon(@PathVariable Long id) {
        log.debug("REST request to delete ControllerActon : {}", id);
        controllerActonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
