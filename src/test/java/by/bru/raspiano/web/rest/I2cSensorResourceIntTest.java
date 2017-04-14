package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.I2cSensor;
import by.bru.raspiano.repository.I2cSensorRepository;
import by.bru.raspiano.service.I2cSensorService;
import by.bru.raspiano.service.dto.I2cSensorDTO;
import by.bru.raspiano.service.mapper.I2cSensorMapper;
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

import by.bru.raspiano.domain.enumeration.SensorType;
/**
 * Test class for the I2cSensorResource REST controller.
 *
 * @see I2cSensorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class I2cSensorResourceIntTest {

    private static final SensorType DEFAULT_SENSOR_TYPE = SensorType.TEMPERATURE;
    private static final SensorType UPDATED_SENSOR_TYPE = SensorType.HUMIDITY;

    private static final Integer DEFAULT_MIN_SENSIVITY = 1;
    private static final Integer UPDATED_MIN_SENSIVITY = 2;

    private static final Integer DEFAULT_MAX_SENSIVITY = 1;
    private static final Integer UPDATED_MAX_SENSIVITY = 2;

    @Autowired
    private I2cSensorRepository i2cSensorRepository;

    @Autowired
    private I2cSensorMapper i2cSensorMapper;

    @Autowired
    private I2cSensorService i2cSensorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restI2cSensorMockMvc;

    private I2cSensor i2cSensor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        I2cSensorResource i2cSensorResource = new I2cSensorResource(i2cSensorService);
        this.restI2cSensorMockMvc = MockMvcBuilders.standaloneSetup(i2cSensorResource)
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
    public static I2cSensor createEntity(EntityManager em) {
        I2cSensor i2cSensor = new I2cSensor()
            .sensorType(DEFAULT_SENSOR_TYPE)
            .minSensivity(DEFAULT_MIN_SENSIVITY)
            .maxSensivity(DEFAULT_MAX_SENSIVITY);
        return i2cSensor;
    }

    @Before
    public void initTest() {
        i2cSensor = createEntity(em);
    }

    @Test
    @Transactional
    public void createI2cSensor() throws Exception {
        int databaseSizeBeforeCreate = i2cSensorRepository.findAll().size();

        // Create the I2cSensor
        I2cSensorDTO i2cSensorDTO = i2cSensorMapper.i2cSensorToI2cSensorDTO(i2cSensor);
        restI2cSensorMockMvc.perform(post("/api/i-2-c-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cSensorDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cSensor in the database
        List<I2cSensor> i2cSensorList = i2cSensorRepository.findAll();
        assertThat(i2cSensorList).hasSize(databaseSizeBeforeCreate + 1);
        I2cSensor testI2cSensor = i2cSensorList.get(i2cSensorList.size() - 1);
        assertThat(testI2cSensor.getSensorType()).isEqualTo(DEFAULT_SENSOR_TYPE);
        assertThat(testI2cSensor.getMinSensivity()).isEqualTo(DEFAULT_MIN_SENSIVITY);
        assertThat(testI2cSensor.getMaxSensivity()).isEqualTo(DEFAULT_MAX_SENSIVITY);
    }

    @Test
    @Transactional
    public void createI2cSensorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = i2cSensorRepository.findAll().size();

        // Create the I2cSensor with an existing ID
        i2cSensor.setId(1L);
        I2cSensorDTO i2cSensorDTO = i2cSensorMapper.i2cSensorToI2cSensorDTO(i2cSensor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restI2cSensorMockMvc.perform(post("/api/i-2-c-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cSensorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<I2cSensor> i2cSensorList = i2cSensorRepository.findAll();
        assertThat(i2cSensorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllI2cSensors() throws Exception {
        // Initialize the database
        i2cSensorRepository.saveAndFlush(i2cSensor);

        // Get all the i2cSensorList
        restI2cSensorMockMvc.perform(get("/api/i-2-c-sensors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i2cSensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].sensorType").value(hasItem(DEFAULT_SENSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minSensivity").value(hasItem(DEFAULT_MIN_SENSIVITY)))
            .andExpect(jsonPath("$.[*].maxSensivity").value(hasItem(DEFAULT_MAX_SENSIVITY)));
    }

    @Test
    @Transactional
    public void getI2cSensor() throws Exception {
        // Initialize the database
        i2cSensorRepository.saveAndFlush(i2cSensor);

        // Get the i2cSensor
        restI2cSensorMockMvc.perform(get("/api/i-2-c-sensors/{id}", i2cSensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(i2cSensor.getId().intValue()))
            .andExpect(jsonPath("$.sensorType").value(DEFAULT_SENSOR_TYPE.toString()))
            .andExpect(jsonPath("$.minSensivity").value(DEFAULT_MIN_SENSIVITY))
            .andExpect(jsonPath("$.maxSensivity").value(DEFAULT_MAX_SENSIVITY));
    }

    @Test
    @Transactional
    public void getNonExistingI2cSensor() throws Exception {
        // Get the i2cSensor
        restI2cSensorMockMvc.perform(get("/api/i-2-c-sensors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateI2cSensor() throws Exception {
        // Initialize the database
        i2cSensorRepository.saveAndFlush(i2cSensor);
        int databaseSizeBeforeUpdate = i2cSensorRepository.findAll().size();

        // Update the i2cSensor
        I2cSensor updatedI2cSensor = i2cSensorRepository.findOne(i2cSensor.getId());
        updatedI2cSensor
            .sensorType(UPDATED_SENSOR_TYPE)
            .minSensivity(UPDATED_MIN_SENSIVITY)
            .maxSensivity(UPDATED_MAX_SENSIVITY);
        I2cSensorDTO i2cSensorDTO = i2cSensorMapper.i2cSensorToI2cSensorDTO(updatedI2cSensor);

        restI2cSensorMockMvc.perform(put("/api/i-2-c-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cSensorDTO)))
            .andExpect(status().isOk());

        // Validate the I2cSensor in the database
        List<I2cSensor> i2cSensorList = i2cSensorRepository.findAll();
        assertThat(i2cSensorList).hasSize(databaseSizeBeforeUpdate);
        I2cSensor testI2cSensor = i2cSensorList.get(i2cSensorList.size() - 1);
        assertThat(testI2cSensor.getSensorType()).isEqualTo(UPDATED_SENSOR_TYPE);
        assertThat(testI2cSensor.getMinSensivity()).isEqualTo(UPDATED_MIN_SENSIVITY);
        assertThat(testI2cSensor.getMaxSensivity()).isEqualTo(UPDATED_MAX_SENSIVITY);
    }

    @Test
    @Transactional
    public void updateNonExistingI2cSensor() throws Exception {
        int databaseSizeBeforeUpdate = i2cSensorRepository.findAll().size();

        // Create the I2cSensor
        I2cSensorDTO i2cSensorDTO = i2cSensorMapper.i2cSensorToI2cSensorDTO(i2cSensor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restI2cSensorMockMvc.perform(put("/api/i-2-c-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cSensorDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cSensor in the database
        List<I2cSensor> i2cSensorList = i2cSensorRepository.findAll();
        assertThat(i2cSensorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteI2cSensor() throws Exception {
        // Initialize the database
        i2cSensorRepository.saveAndFlush(i2cSensor);
        int databaseSizeBeforeDelete = i2cSensorRepository.findAll().size();

        // Get the i2cSensor
        restI2cSensorMockMvc.perform(delete("/api/i-2-c-sensors/{id}", i2cSensor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<I2cSensor> i2cSensorList = i2cSensorRepository.findAll();
        assertThat(i2cSensorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I2cSensor.class);
    }
}
