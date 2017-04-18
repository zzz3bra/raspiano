package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.Measurement;
import by.bru.raspiano.domain.I2cSensor;
import by.bru.raspiano.domain.Climate;
import by.bru.raspiano.repository.MeasurementRepository;
import by.bru.raspiano.service.MeasurementService;
import by.bru.raspiano.service.dto.MeasurementDTO;
import by.bru.raspiano.service.mapper.MeasurementMapper;
import by.bru.raspiano.web.rest.errors.ExceptionTranslator;

import com.google.common.collect.Lists;
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
import org.springframework.test.web.servlet.MvcResult;
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
 * Test class for the MeasurementResource REST controller.
 *
 * @see MeasurementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class MeasurementResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementMapper measurementMapper;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeasurementMockMvc;

    private Measurement measurement;
    private Measurement oldMeasurement;
    private Measurement futureMeasurement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MeasurementResource measurementResource = new MeasurementResource(measurementService);
        this.restMeasurementMockMvc = MockMvcBuilders.standaloneSetup(measurementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Measurement createEntity(EntityManager em) {
        Measurement measurement = new Measurement()
            .dateTime(DEFAULT_DATE_TIME)
            .value(DEFAULT_VALUE);
        // Add required entity
        I2cSensor source = I2cSensorResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        measurement.setSource(source);
        // Add required entity
        Climate climate = ClimateResourceIntTest.createEntity(em);
        em.persist(climate);
        em.flush();
        measurement.setClimate(climate);
        return measurement;
    }

    @Before
    public void initTest() {
        measurement = createEntity(em);
        oldMeasurement = createEntity(em).dateTime(ZonedDateTime.now().minusDays(2));
        futureMeasurement = createEntity(em).dateTime(ZonedDateTime.now().plusDays(2));
    }

    @Test
    @Transactional
    public void createMeasurement() throws Exception {
        int databaseSizeBeforeCreate = measurementRepository.findAll().size();

        // Create the Measurement
        MeasurementDTO measurementDTO = measurementMapper.measurementToMeasurementDTO(measurement);
        restMeasurementMockMvc.perform(post("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isCreated());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeCreate + 1);
        Measurement testMeasurement = measurementList.get(measurementList.size() - 1);
        assertThat(testMeasurement.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testMeasurement.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createMeasurementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measurementRepository.findAll().size();

        // Create the Measurement with an existing ID
        measurement.setId(1L);
        MeasurementDTO measurementDTO = measurementMapper.measurementToMeasurementDTO(measurement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasurementMockMvc.perform(post("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeasurements() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        // Get all the measurementList
        restMeasurementMockMvc.perform(get("/api/measurements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        // Get the measurement
        restMeasurementMockMvc.perform(get("/api/measurements/{id}", measurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(measurement.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingMeasurement() throws Exception {
        // Get the measurement
        restMeasurementMockMvc.perform(get("/api/measurements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);
        int databaseSizeBeforeUpdate = measurementRepository.findAll().size();

        // Update the measurement
        Measurement updatedMeasurement = measurementRepository.findOne(measurement.getId());
        updatedMeasurement
            .dateTime(UPDATED_DATE_TIME)
            .value(UPDATED_VALUE);
        MeasurementDTO measurementDTO = measurementMapper.measurementToMeasurementDTO(updatedMeasurement);

        restMeasurementMockMvc.perform(put("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isOk());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeUpdate);
        Measurement testMeasurement = measurementList.get(measurementList.size() - 1);
        assertThat(testMeasurement.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testMeasurement.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasurement() throws Exception {
        int databaseSizeBeforeUpdate = measurementRepository.findAll().size();

        // Create the Measurement
        MeasurementDTO measurementDTO = measurementMapper.measurementToMeasurementDTO(measurement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeasurementMockMvc.perform(put("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isCreated());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);
        int databaseSizeBeforeDelete = measurementRepository.findAll().size();

        // Get the measurement
        restMeasurementMockMvc.perform(delete("/api/measurements/{id}", measurement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Measurement.class);
    }

    @Test
    @Transactional
    public void getAllMeasurementsWithNullFromDateShouldReturnAllStartingFromUnixEpoch() throws Exception {
        // Initialize the database
        measurementRepository.save(Lists.newArrayList(oldMeasurement, futureMeasurement));
        measurementRepository.flush();

        // Get all the measurementList
        restMeasurementMockMvc.perform(get("/api/measurements?sort=id,desc").requestAttr("toDate", ZonedDateTime.now().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oldMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(oldMeasurement.getDateTime()))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getAllMeasurementsWithNullToDateShouldReturnAllUntilNow() throws Exception {
        // Initialize the database
        measurementRepository.save(Lists.newArrayList(oldMeasurement, futureMeasurement));
        measurementRepository.flush();

        // Get all the measurementList
        restMeasurementMockMvc.perform(get("/api/measurements?sort=id,desc").requestAttr("fromDate", ZonedDateTime.now().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oldMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(oldMeasurement.getDateTime()))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
}
