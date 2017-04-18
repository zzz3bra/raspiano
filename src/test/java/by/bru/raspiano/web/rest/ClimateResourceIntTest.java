package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.Climate;
import by.bru.raspiano.repository.ClimateRepository;
import by.bru.raspiano.service.ClimateService;
import by.bru.raspiano.service.dto.ClimateDTO;
import by.bru.raspiano.service.mapper.ClimateMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClimateResource REST controller.
 *
 * @see ClimateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class ClimateResourceIntTest {

    private static final Integer DEFAULT_MIN_DESIRED_TEMPERATURE = 1;
    private static final Integer UPDATED_MIN_DESIRED_TEMPERATURE = 2;

    private static final Integer DEFAULT_MAX_DESIRED_TEMPERATURE = 1;
    private static final Integer UPDATED_MAX_DESIRED_TEMPERATURE = 2;

    private static final Integer DEFAULT_MIN_DESIRED_HUMIDITY = 1;
    private static final Integer UPDATED_MIN_DESIRED_HUMIDITY = 2;

    private static final Integer DEFAULT_MAX_DESIRED_HUMIDITY = 1;
    private static final Integer UPDATED_MAX_DESIRED_HUMIDITY = 2;

    @Autowired
    private ClimateRepository climateRepository;

    @Autowired
    private ClimateMapper climateMapper;

    @Autowired
    private ClimateService climateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClimateMockMvc;

    private Climate climate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClimateResource climateResource = new ClimateResource(climateService);
        this.restClimateMockMvc = MockMvcBuilders.standaloneSetup(climateResource)
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
    public static Climate createEntity(EntityManager em) {
        Climate climate = new Climate()
            .minDesiredTemperature(DEFAULT_MIN_DESIRED_TEMPERATURE)
            .maxDesiredTemperature(DEFAULT_MAX_DESIRED_TEMPERATURE)
            .minDesiredHumidity(DEFAULT_MIN_DESIRED_HUMIDITY)
            .maxDesiredHumidity(DEFAULT_MAX_DESIRED_HUMIDITY);
        return climate;
    }

    @Before
    public void initTest() {
        climate = createEntity(em);
    }

    @Test
    @Transactional
    public void createClimate() throws Exception {
        int databaseSizeBeforeCreate = climateRepository.findAll().size();

        // Create the Climate
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);
        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isCreated());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeCreate + 1);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getMinDesiredTemperature()).isEqualTo(DEFAULT_MIN_DESIRED_TEMPERATURE);
        assertThat(testClimate.getMaxDesiredTemperature()).isEqualTo(DEFAULT_MAX_DESIRED_TEMPERATURE);
        assertThat(testClimate.getMinDesiredHumidity()).isEqualTo(DEFAULT_MIN_DESIRED_HUMIDITY);
        assertThat(testClimate.getMaxDesiredHumidity()).isEqualTo(DEFAULT_MAX_DESIRED_HUMIDITY);
    }

    @Test
    @Transactional
    public void createClimateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = climateRepository.findAll().size();

        // Create the Climate with an existing ID
        climate.setId(1L);
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMinDesiredTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = climateRepository.findAll().size();
        // set the field null
        climate.setMinDesiredTemperature(null);

        // Create the Climate, which fails.
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isBadRequest());

        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxDesiredTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = climateRepository.findAll().size();
        // set the field null
        climate.setMaxDesiredTemperature(null);

        // Create the Climate, which fails.
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isBadRequest());

        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinDesiredHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = climateRepository.findAll().size();
        // set the field null
        climate.setMinDesiredHumidity(null);

        // Create the Climate, which fails.
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isBadRequest());

        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxDesiredHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = climateRepository.findAll().size();
        // set the field null
        climate.setMaxDesiredHumidity(null);

        // Create the Climate, which fails.
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        restClimateMockMvc.perform(post("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isBadRequest());

        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClimates() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        // Get all the climateList
        restClimateMockMvc.perform(get("/api/climates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(climate.getId().intValue())))
            .andExpect(jsonPath("$.[*].minDesiredTemperature").value(hasItem(DEFAULT_MIN_DESIRED_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].maxDesiredTemperature").value(hasItem(DEFAULT_MAX_DESIRED_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].minDesiredHumidity").value(hasItem(DEFAULT_MIN_DESIRED_HUMIDITY)))
            .andExpect(jsonPath("$.[*].maxDesiredHumidity").value(hasItem(DEFAULT_MAX_DESIRED_HUMIDITY)));
    }

    @Test
    @Transactional
    public void getClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);

        // Get the climate
        restClimateMockMvc.perform(get("/api/climates/{id}", climate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(climate.getId().intValue()))
            .andExpect(jsonPath("$.minDesiredTemperature").value(DEFAULT_MIN_DESIRED_TEMPERATURE))
            .andExpect(jsonPath("$.maxDesiredTemperature").value(DEFAULT_MAX_DESIRED_TEMPERATURE))
            .andExpect(jsonPath("$.minDesiredHumidity").value(DEFAULT_MIN_DESIRED_HUMIDITY))
            .andExpect(jsonPath("$.maxDesiredHumidity").value(DEFAULT_MAX_DESIRED_HUMIDITY));
    }

    @Test
    @Transactional
    public void getNonExistingClimate() throws Exception {
        // Get the climate
        restClimateMockMvc.perform(get("/api/climates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();

        // Update the climate
        Climate updatedClimate = climateRepository.findOne(climate.getId());
        updatedClimate
            .minDesiredTemperature(UPDATED_MIN_DESIRED_TEMPERATURE)
            .maxDesiredTemperature(UPDATED_MAX_DESIRED_TEMPERATURE)
            .minDesiredHumidity(UPDATED_MIN_DESIRED_HUMIDITY)
            .maxDesiredHumidity(UPDATED_MAX_DESIRED_HUMIDITY);
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(updatedClimate);

        restClimateMockMvc.perform(put("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isOk());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate);
        Climate testClimate = climateList.get(climateList.size() - 1);
        assertThat(testClimate.getMinDesiredTemperature()).isEqualTo(UPDATED_MIN_DESIRED_TEMPERATURE);
        assertThat(testClimate.getMaxDesiredTemperature()).isEqualTo(UPDATED_MAX_DESIRED_TEMPERATURE);
        assertThat(testClimate.getMinDesiredHumidity()).isEqualTo(UPDATED_MIN_DESIRED_HUMIDITY);
        assertThat(testClimate.getMaxDesiredHumidity()).isEqualTo(UPDATED_MAX_DESIRED_HUMIDITY);
    }

    @Test
    @Transactional
    public void updateNonExistingClimate() throws Exception {
        int databaseSizeBeforeUpdate = climateRepository.findAll().size();

        // Create the Climate
        ClimateDTO climateDTO = climateMapper.climateToClimateDTO(climate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClimateMockMvc.perform(put("/api/climates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(climateDTO)))
            .andExpect(status().isCreated());

        // Validate the Climate in the database
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClimate() throws Exception {
        // Initialize the database
        climateRepository.saveAndFlush(climate);
        int databaseSizeBeforeDelete = climateRepository.findAll().size();

        // Get the climate
        restClimateMockMvc.perform(delete("/api/climates/{id}", climate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Climate> climateList = climateRepository.findAll();
        assertThat(climateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Climate.class);
    }
}
