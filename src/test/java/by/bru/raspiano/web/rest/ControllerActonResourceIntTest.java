package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.ControllerActon;
import by.bru.raspiano.repository.ControllerActonRepository;
import by.bru.raspiano.service.ControllerActonService;
import by.bru.raspiano.service.dto.ControllerActonDTO;
import by.bru.raspiano.service.mapper.ControllerActonMapper;
import by.bru.raspiano.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static by.bru.raspiano.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ControllerActonResource REST controller.
 *
 * @see ControllerActonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class ControllerActonResourceIntTest {

    private static final ZonedDateTime DEFAULT_ACTION_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTION_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTION_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTION_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ControllerActonRepository controllerActonRepository;

    @Autowired
    private ControllerActonMapper controllerActonMapper;

    @Autowired
    private ControllerActonService controllerActonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restControllerActonMockMvc;

    private ControllerActon controllerActon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ControllerActonResource controllerActonResource = new ControllerActonResource(controllerActonService);
        this.restControllerActonMockMvc = MockMvcBuilders.standaloneSetup(controllerActonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControllerActon createEntity(EntityManager em) {
        ControllerActon controllerActon = new ControllerActon()
            .actionStart(DEFAULT_ACTION_START)
            .actionEnd(DEFAULT_ACTION_END);
        return controllerActon;
    }

    @Before
    public void initTest() {
        controllerActon = createEntity(em);
    }

    @Test
    @Transactional
    public void createControllerActon() throws Exception {
        int databaseSizeBeforeCreate = controllerActonRepository.findAll().size();

        // Create the ControllerActon
        ControllerActonDTO controllerActonDTO = controllerActonMapper.controllerActonToControllerActonDTO(controllerActon);
        restControllerActonMockMvc.perform(post("/api/controller-actons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controllerActonDTO)))
            .andExpect(status().isCreated());

        // Validate the ControllerActon in the database
        List<ControllerActon> controllerActonList = controllerActonRepository.findAll();
        assertThat(controllerActonList).hasSize(databaseSizeBeforeCreate + 1);
        ControllerActon testControllerActon = controllerActonList.get(controllerActonList.size() - 1);
        assertThat(testControllerActon.getActionStart()).isEqualTo(DEFAULT_ACTION_START);
        assertThat(testControllerActon.getActionEnd()).isEqualTo(DEFAULT_ACTION_END);
    }

    @Test
    @Transactional
    public void createControllerActonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controllerActonRepository.findAll().size();

        // Create the ControllerActon with an existing ID
        controllerActon.setId(1L);
        ControllerActonDTO controllerActonDTO = controllerActonMapper.controllerActonToControllerActonDTO(controllerActon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControllerActonMockMvc.perform(post("/api/controller-actons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controllerActonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ControllerActon> controllerActonList = controllerActonRepository.findAll();
        assertThat(controllerActonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllControllerActons() throws Exception {
        // Initialize the database
        controllerActonRepository.saveAndFlush(controllerActon);

        // Get all the controllerActonList
        restControllerActonMockMvc.perform(get("/api/controller-actons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controllerActon.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionStart").value(hasItem(sameInstant(DEFAULT_ACTION_START))))
            .andExpect(jsonPath("$.[*].actionEnd").value(hasItem(sameInstant(DEFAULT_ACTION_END))));
    }

    @Test
    @Transactional
    public void getControllerActon() throws Exception {
        // Initialize the database
        controllerActonRepository.saveAndFlush(controllerActon);

        // Get the controllerActon
        restControllerActonMockMvc.perform(get("/api/controller-actons/{id}", controllerActon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(controllerActon.getId().intValue()))
            .andExpect(jsonPath("$.actionStart").value(sameInstant(DEFAULT_ACTION_START)))
            .andExpect(jsonPath("$.actionEnd").value(sameInstant(DEFAULT_ACTION_END)));
    }

    @Test
    @Transactional
    public void getNonExistingControllerActon() throws Exception {
        // Get the controllerActon
        restControllerActonMockMvc.perform(get("/api/controller-actons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControllerActon() throws Exception {
        // Initialize the database
        controllerActonRepository.saveAndFlush(controllerActon);
        int databaseSizeBeforeUpdate = controllerActonRepository.findAll().size();

        // Update the controllerActon
        ControllerActon updatedControllerActon = controllerActonRepository.findOne(controllerActon.getId());
        updatedControllerActon
            .actionStart(UPDATED_ACTION_START)
            .actionEnd(UPDATED_ACTION_END);
        ControllerActonDTO controllerActonDTO = controllerActonMapper.controllerActonToControllerActonDTO(updatedControllerActon);

        restControllerActonMockMvc.perform(put("/api/controller-actons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controllerActonDTO)))
            .andExpect(status().isOk());

        // Validate the ControllerActon in the database
        List<ControllerActon> controllerActonList = controllerActonRepository.findAll();
        assertThat(controllerActonList).hasSize(databaseSizeBeforeUpdate);
        ControllerActon testControllerActon = controllerActonList.get(controllerActonList.size() - 1);
        assertThat(testControllerActon.getActionStart()).isEqualTo(UPDATED_ACTION_START);
        assertThat(testControllerActon.getActionEnd()).isEqualTo(UPDATED_ACTION_END);
    }

    @Test
    @Transactional
    public void updateNonExistingControllerActon() throws Exception {
        int databaseSizeBeforeUpdate = controllerActonRepository.findAll().size();

        // Create the ControllerActon
        ControllerActonDTO controllerActonDTO = controllerActonMapper.controllerActonToControllerActonDTO(controllerActon);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restControllerActonMockMvc.perform(put("/api/controller-actons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controllerActonDTO)))
            .andExpect(status().isCreated());

        // Validate the ControllerActon in the database
        List<ControllerActon> controllerActonList = controllerActonRepository.findAll();
        assertThat(controllerActonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteControllerActon() throws Exception {
        // Initialize the database
        controllerActonRepository.saveAndFlush(controllerActon);
        int databaseSizeBeforeDelete = controllerActonRepository.findAll().size();

        // Get the controllerActon
        restControllerActonMockMvc.perform(delete("/api/controller-actons/{id}", controllerActon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ControllerActon> controllerActonList = controllerActonRepository.findAll();
        assertThat(controllerActonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControllerActon.class);
    }
}
