package by.bru.raspiano.web.rest;

import by.bru.raspiano.RaspianoApp;

import by.bru.raspiano.domain.Raspberry;
import by.bru.raspiano.domain.Climate;
import by.bru.raspiano.repository.RaspberryRepository;
import by.bru.raspiano.service.RaspberryService;
import by.bru.raspiano.service.dto.RaspberryDTO;
import by.bru.raspiano.service.mapper.RaspberryMapper;
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
 * Test class for the RaspberryResource REST controller.
 *
 * @see RaspberryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaspianoApp.class)
public class RaspberryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RaspberryRepository raspberryRepository;

    @Autowired
    private RaspberryMapper raspberryMapper;

    @Autowired
    private RaspberryService raspberryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaspberryMockMvc;

    private Raspberry raspberry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaspberryResource raspberryResource = new RaspberryResource(raspberryService);
        this.restRaspberryMockMvc = MockMvcBuilders.standaloneSetup(raspberryResource)
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
    public static Raspberry createEntity(EntityManager em) {
        Raspberry raspberry = new Raspberry()
            .name(DEFAULT_NAME);
        // Add required entity
        Climate climate = ClimateResourceIntTest.createEntity(em);
        em.persist(climate);
        em.flush();
        raspberry.setClimate(climate);
        return raspberry;
    }

    @Before
    public void initTest() {
        raspberry = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaspberry() throws Exception {
        int databaseSizeBeforeCreate = raspberryRepository.findAll().size();

        // Create the Raspberry
        RaspberryDTO raspberryDTO = raspberryMapper.raspberryToRaspberryDTO(raspberry);
        restRaspberryMockMvc.perform(post("/api/raspberries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raspberryDTO)))
            .andExpect(status().isCreated());

        // Validate the Raspberry in the database
        List<Raspberry> raspberryList = raspberryRepository.findAll();
        assertThat(raspberryList).hasSize(databaseSizeBeforeCreate + 1);
        Raspberry testRaspberry = raspberryList.get(raspberryList.size() - 1);
        assertThat(testRaspberry.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRaspberryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raspberryRepository.findAll().size();

        // Create the Raspberry with an existing ID
        raspberry.setId(1L);
        RaspberryDTO raspberryDTO = raspberryMapper.raspberryToRaspberryDTO(raspberry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaspberryMockMvc.perform(post("/api/raspberries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raspberryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Raspberry> raspberryList = raspberryRepository.findAll();
        assertThat(raspberryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRaspberries() throws Exception {
        // Initialize the database
        raspberryRepository.saveAndFlush(raspberry);

        // Get all the raspberryList
        restRaspberryMockMvc.perform(get("/api/raspberries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raspberry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRaspberry() throws Exception {
        // Initialize the database
        raspberryRepository.saveAndFlush(raspberry);

        // Get the raspberry
        restRaspberryMockMvc.perform(get("/api/raspberries/{id}", raspberry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raspberry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaspberry() throws Exception {
        // Get the raspberry
        restRaspberryMockMvc.perform(get("/api/raspberries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaspberry() throws Exception {
        // Initialize the database
        raspberryRepository.saveAndFlush(raspberry);
        int databaseSizeBeforeUpdate = raspberryRepository.findAll().size();

        // Update the raspberry
        Raspberry updatedRaspberry = raspberryRepository.findOne(raspberry.getId());
        updatedRaspberry
            .name(UPDATED_NAME);
        RaspberryDTO raspberryDTO = raspberryMapper.raspberryToRaspberryDTO(updatedRaspberry);

        restRaspberryMockMvc.perform(put("/api/raspberries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raspberryDTO)))
            .andExpect(status().isOk());

        // Validate the Raspberry in the database
        List<Raspberry> raspberryList = raspberryRepository.findAll();
        assertThat(raspberryList).hasSize(databaseSizeBeforeUpdate);
        Raspberry testRaspberry = raspberryList.get(raspberryList.size() - 1);
        assertThat(testRaspberry.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRaspberry() throws Exception {
        int databaseSizeBeforeUpdate = raspberryRepository.findAll().size();

        // Create the Raspberry
        RaspberryDTO raspberryDTO = raspberryMapper.raspberryToRaspberryDTO(raspberry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaspberryMockMvc.perform(put("/api/raspberries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raspberryDTO)))
            .andExpect(status().isCreated());

        // Validate the Raspberry in the database
        List<Raspberry> raspberryList = raspberryRepository.findAll();
        assertThat(raspberryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaspberry() throws Exception {
        // Initialize the database
        raspberryRepository.saveAndFlush(raspberry);
        int databaseSizeBeforeDelete = raspberryRepository.findAll().size();

        // Get the raspberry
        restRaspberryMockMvc.perform(delete("/api/raspberries/{id}", raspberry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Raspberry> raspberryList = raspberryRepository.findAll();
        assertThat(raspberryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raspberry.class);
    }
}
