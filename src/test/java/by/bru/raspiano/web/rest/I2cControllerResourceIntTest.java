package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.I2cController;
import by.bru.raspiano.repository.I2cControllerRepository;
import by.bru.raspiano.service.I2cControllerService;
import by.bru.raspiano.service.dto.I2cControllerDTO;
import by.bru.raspiano.service.mapper.I2cControllerMapper;
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

import by.bru.raspiano.domain.enumeration.ControllerType;
/**
 * Test class for the I2cControllerResource REST controller.
 *
 * @see I2cControllerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class I2cControllerResourceIntTest {

    private static final ControllerType DEFAULT_CONTROLLER_TYPE = ControllerType.MOTOR;
    private static final ControllerType UPDATED_CONTROLLER_TYPE = ControllerType.VALVE;

    private static final Integer DEFAULT_TURN_OFF_DELAY_MS = 1;
    private static final Integer UPDATED_TURN_OFF_DELAY_MS = 2;

    private static final Integer DEFAULT_TURN_ON_DELAY_MS = 1;
    private static final Integer UPDATED_TURN_ON_DELAY_MS = 2;

    @Autowired
    private I2cControllerRepository i2cControllerRepository;

    @Autowired
    private I2cControllerMapper i2cControllerMapper;

    @Autowired
    private I2cControllerService i2cControllerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restI2cControllerMockMvc;

    private I2cController i2cController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        I2cControllerResource i2cControllerResource = new I2cControllerResource(i2cControllerService);
        this.restI2cControllerMockMvc = MockMvcBuilders.standaloneSetup(i2cControllerResource)
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
    public static I2cController createEntity(EntityManager em) {
        I2cController i2cController = new I2cController()
            .controllerType(DEFAULT_CONTROLLER_TYPE)
            .turnOffDelayMs(DEFAULT_TURN_OFF_DELAY_MS)
            .turnOnDelayMs(DEFAULT_TURN_ON_DELAY_MS);
        return i2cController;
    }

    @Before
    public void initTest() {
        i2cController = createEntity(em);
    }

    @Test
    @Transactional
    public void createI2cController() throws Exception {
        int databaseSizeBeforeCreate = i2cControllerRepository.findAll().size();

        // Create the I2cController
        I2cControllerDTO i2cControllerDTO = i2cControllerMapper.i2cControllerToI2cControllerDTO(i2cController);
        restI2cControllerMockMvc.perform(post("/api/i-2-c-controllers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cControllerDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cController in the database
        List<I2cController> i2cControllerList = i2cControllerRepository.findAll();
        assertThat(i2cControllerList).hasSize(databaseSizeBeforeCreate + 1);
        I2cController testI2cController = i2cControllerList.get(i2cControllerList.size() - 1);
        assertThat(testI2cController.getControllerType()).isEqualTo(DEFAULT_CONTROLLER_TYPE);
        assertThat(testI2cController.getTurnOffDelayMs()).isEqualTo(DEFAULT_TURN_OFF_DELAY_MS);
        assertThat(testI2cController.getTurnOnDelayMs()).isEqualTo(DEFAULT_TURN_ON_DELAY_MS);
    }

    @Test
    @Transactional
    public void createI2cControllerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = i2cControllerRepository.findAll().size();

        // Create the I2cController with an existing ID
        i2cController.setId(1L);
        I2cControllerDTO i2cControllerDTO = i2cControllerMapper.i2cControllerToI2cControllerDTO(i2cController);

        // An entity with an existing ID cannot be created, so this API call must fail
        restI2cControllerMockMvc.perform(post("/api/i-2-c-controllers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cControllerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<I2cController> i2cControllerList = i2cControllerRepository.findAll();
        assertThat(i2cControllerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllI2cControllers() throws Exception {
        // Initialize the database
        i2cControllerRepository.saveAndFlush(i2cController);

        // Get all the i2cControllerList
        restI2cControllerMockMvc.perform(get("/api/i-2-c-controllers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i2cController.getId().intValue())))
            .andExpect(jsonPath("$.[*].controllerType").value(hasItem(DEFAULT_CONTROLLER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].turnOffDelayMs").value(hasItem(DEFAULT_TURN_OFF_DELAY_MS)))
            .andExpect(jsonPath("$.[*].turnOnDelayMs").value(hasItem(DEFAULT_TURN_ON_DELAY_MS)));
    }

    @Test
    @Transactional
    public void getI2cController() throws Exception {
        // Initialize the database
        i2cControllerRepository.saveAndFlush(i2cController);

        // Get the i2cController
        restI2cControllerMockMvc.perform(get("/api/i-2-c-controllers/{id}", i2cController.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(i2cController.getId().intValue()))
            .andExpect(jsonPath("$.controllerType").value(DEFAULT_CONTROLLER_TYPE.toString()))
            .andExpect(jsonPath("$.turnOffDelayMs").value(DEFAULT_TURN_OFF_DELAY_MS))
            .andExpect(jsonPath("$.turnOnDelayMs").value(DEFAULT_TURN_ON_DELAY_MS));
    }

    @Test
    @Transactional
    public void getNonExistingI2cController() throws Exception {
        // Get the i2cController
        restI2cControllerMockMvc.perform(get("/api/i-2-c-controllers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateI2cController() throws Exception {
        // Initialize the database
        i2cControllerRepository.saveAndFlush(i2cController);
        int databaseSizeBeforeUpdate = i2cControllerRepository.findAll().size();

        // Update the i2cController
        I2cController updatedI2cController = i2cControllerRepository.findOne(i2cController.getId());
        updatedI2cController
            .controllerType(UPDATED_CONTROLLER_TYPE)
            .turnOffDelayMs(UPDATED_TURN_OFF_DELAY_MS)
            .turnOnDelayMs(UPDATED_TURN_ON_DELAY_MS);
        I2cControllerDTO i2cControllerDTO = i2cControllerMapper.i2cControllerToI2cControllerDTO(updatedI2cController);

        restI2cControllerMockMvc.perform(put("/api/i-2-c-controllers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cControllerDTO)))
            .andExpect(status().isOk());

        // Validate the I2cController in the database
        List<I2cController> i2cControllerList = i2cControllerRepository.findAll();
        assertThat(i2cControllerList).hasSize(databaseSizeBeforeUpdate);
        I2cController testI2cController = i2cControllerList.get(i2cControllerList.size() - 1);
        assertThat(testI2cController.getControllerType()).isEqualTo(UPDATED_CONTROLLER_TYPE);
        assertThat(testI2cController.getTurnOffDelayMs()).isEqualTo(UPDATED_TURN_OFF_DELAY_MS);
        assertThat(testI2cController.getTurnOnDelayMs()).isEqualTo(UPDATED_TURN_ON_DELAY_MS);
    }

    @Test
    @Transactional
    public void updateNonExistingI2cController() throws Exception {
        int databaseSizeBeforeUpdate = i2cControllerRepository.findAll().size();

        // Create the I2cController
        I2cControllerDTO i2cControllerDTO = i2cControllerMapper.i2cControllerToI2cControllerDTO(i2cController);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restI2cControllerMockMvc.perform(put("/api/i-2-c-controllers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cControllerDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cController in the database
        List<I2cController> i2cControllerList = i2cControllerRepository.findAll();
        assertThat(i2cControllerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteI2cController() throws Exception {
        // Initialize the database
        i2cControllerRepository.saveAndFlush(i2cController);
        int databaseSizeBeforeDelete = i2cControllerRepository.findAll().size();

        // Get the i2cController
        restI2cControllerMockMvc.perform(delete("/api/i-2-c-controllers/{id}", i2cController.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<I2cController> i2cControllerList = i2cControllerRepository.findAll();
        assertThat(i2cControllerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I2cController.class);
    }
}
