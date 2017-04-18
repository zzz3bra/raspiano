package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.I2cDevice;
import by.bru.raspiano.domain.Raspberry;
import by.bru.raspiano.repository.I2cDeviceRepository;
import by.bru.raspiano.service.I2cDeviceService;
import by.bru.raspiano.service.dto.I2cDeviceDTO;
import by.bru.raspiano.service.mapper.I2cDeviceMapper;
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

import by.bru.raspiano.domain.enumeration.DeviceType;
/**
 * Test class for the I2cDeviceResource REST controller.
 *
 * @see I2cDeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class I2cDeviceResourceIntTest {

    private static final DeviceType DEFAULT_DEVICE_TYPE = DeviceType.SENSOR;
    private static final DeviceType UPDATED_DEVICE_TYPE = DeviceType.CONTROLLER;

    private static final Integer DEFAULT_BUS_ADDRESS = 1;
    private static final Integer UPDATED_BUS_ADDRESS = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private I2cDeviceRepository i2cDeviceRepository;

    @Autowired
    private I2cDeviceMapper i2cDeviceMapper;

    @Autowired
    private I2cDeviceService i2cDeviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restI2cDeviceMockMvc;

    private I2cDevice i2cDevice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        I2cDeviceResource i2cDeviceResource = new I2cDeviceResource(i2cDeviceService);
        this.restI2cDeviceMockMvc = MockMvcBuilders.standaloneSetup(i2cDeviceResource)
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
    public static I2cDevice createEntity(EntityManager em) {
        I2cDevice i2cDevice = new I2cDevice()
            .deviceType(DEFAULT_DEVICE_TYPE)
            .busAddress(DEFAULT_BUS_ADDRESS)
            .name(DEFAULT_NAME);
        // Add required entity
        Raspberry device = RaspberryResourceIntTest.createEntity(em);
        em.persist(device);
        em.flush();
        i2cDevice.setDevice(device);
        return i2cDevice;
    }

    @Before
    public void initTest() {
        i2cDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createI2cDevice() throws Exception {
        int databaseSizeBeforeCreate = i2cDeviceRepository.findAll().size();

        // Create the I2cDevice
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);
        restI2cDeviceMockMvc.perform(post("/api/i-2-c-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cDevice in the database
        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        I2cDevice testI2cDevice = i2cDeviceList.get(i2cDeviceList.size() - 1);
        assertThat(testI2cDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testI2cDevice.getBusAddress()).isEqualTo(DEFAULT_BUS_ADDRESS);
        assertThat(testI2cDevice.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createI2cDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = i2cDeviceRepository.findAll().size();

        // Create the I2cDevice with an existing ID
        i2cDevice.setId(1L);
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restI2cDeviceMockMvc.perform(post("/api/i-2-c-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeviceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = i2cDeviceRepository.findAll().size();
        // set the field null
        i2cDevice.setDeviceType(null);

        // Create the I2cDevice, which fails.
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);

        restI2cDeviceMockMvc.perform(post("/api/i-2-c-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cDeviceDTO)))
            .andExpect(status().isBadRequest());

        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllI2cDevices() throws Exception {
        // Initialize the database
        i2cDeviceRepository.saveAndFlush(i2cDevice);

        // Get all the i2cDeviceList
        restI2cDeviceMockMvc.perform(get("/api/i-2-c-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i2cDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].busAddress").value(hasItem(DEFAULT_BUS_ADDRESS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getI2cDevice() throws Exception {
        // Initialize the database
        i2cDeviceRepository.saveAndFlush(i2cDevice);

        // Get the i2cDevice
        restI2cDeviceMockMvc.perform(get("/api/i-2-c-devices/{id}", i2cDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(i2cDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.busAddress").value(DEFAULT_BUS_ADDRESS))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingI2cDevice() throws Exception {
        // Get the i2cDevice
        restI2cDeviceMockMvc.perform(get("/api/i-2-c-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateI2cDevice() throws Exception {
        // Initialize the database
        i2cDeviceRepository.saveAndFlush(i2cDevice);
        int databaseSizeBeforeUpdate = i2cDeviceRepository.findAll().size();

        // Update the i2cDevice
        I2cDevice updatedI2cDevice = i2cDeviceRepository.findOne(i2cDevice.getId());
        updatedI2cDevice
            .deviceType(UPDATED_DEVICE_TYPE)
            .busAddress(UPDATED_BUS_ADDRESS)
            .name(UPDATED_NAME);
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(updatedI2cDevice);

        restI2cDeviceMockMvc.perform(put("/api/i-2-c-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cDeviceDTO)))
            .andExpect(status().isOk());

        // Validate the I2cDevice in the database
        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeUpdate);
        I2cDevice testI2cDevice = i2cDeviceList.get(i2cDeviceList.size() - 1);
        assertThat(testI2cDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testI2cDevice.getBusAddress()).isEqualTo(UPDATED_BUS_ADDRESS);
        assertThat(testI2cDevice.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingI2cDevice() throws Exception {
        int databaseSizeBeforeUpdate = i2cDeviceRepository.findAll().size();

        // Create the I2cDevice
        I2cDeviceDTO i2cDeviceDTO = i2cDeviceMapper.i2cDeviceToI2cDeviceDTO(i2cDevice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restI2cDeviceMockMvc.perform(put("/api/i-2-c-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i2cDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the I2cDevice in the database
        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteI2cDevice() throws Exception {
        // Initialize the database
        i2cDeviceRepository.saveAndFlush(i2cDevice);
        int databaseSizeBeforeDelete = i2cDeviceRepository.findAll().size();

        // Get the i2cDevice
        restI2cDeviceMockMvc.perform(delete("/api/i-2-c-devices/{id}", i2cDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<I2cDevice> i2cDeviceList = i2cDeviceRepository.findAll();
        assertThat(i2cDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I2cDevice.class);
    }
}
