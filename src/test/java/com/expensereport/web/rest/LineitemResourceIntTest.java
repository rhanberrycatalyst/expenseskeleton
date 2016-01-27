package com.expensereport.web.rest;

import com.expensereport.Application;
import com.expensereport.domain.Lineitem;
import com.expensereport.repository.LineitemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LineitemResource REST controller.
 *
 * @see LineitemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LineitemResourceIntTest {


    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    @Inject
    private LineitemRepository lineitemRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLineitemMockMvc;

    private Lineitem lineitem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LineitemResource lineitemResource = new LineitemResource();
        ReflectionTestUtils.setField(lineitemResource, "lineitemRepository", lineitemRepository);
        this.restLineitemMockMvc = MockMvcBuilders.standaloneSetup(lineitemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lineitem = new Lineitem();
        lineitem.setAmount(DEFAULT_AMOUNT);
        lineitem.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLineitem() throws Exception {
        int databaseSizeBeforeCreate = lineitemRepository.findAll().size();

        // Create the Lineitem

        restLineitemMockMvc.perform(post("/api/lineitems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineitem)))
                .andExpect(status().isCreated());

        // Validate the Lineitem in the database
        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems).hasSize(databaseSizeBeforeCreate + 1);
        Lineitem testLineitem = lineitems.get(lineitems.size() - 1);
        assertThat(testLineitem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testLineitem.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = lineitemRepository.findAll().size();
        // set the field null
        lineitem.setAmount(null);

        // Create the Lineitem, which fails.

        restLineitemMockMvc.perform(post("/api/lineitems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineitem)))
                .andExpect(status().isBadRequest());

        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lineitemRepository.findAll().size();
        // set the field null
        lineitem.setType(null);

        // Create the Lineitem, which fails.

        restLineitemMockMvc.perform(post("/api/lineitems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineitem)))
                .andExpect(status().isBadRequest());

        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLineitems() throws Exception {
        // Initialize the database
        lineitemRepository.saveAndFlush(lineitem);

        // Get all the lineitems
        restLineitemMockMvc.perform(get("/api/lineitems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lineitem.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getLineitem() throws Exception {
        // Initialize the database
        lineitemRepository.saveAndFlush(lineitem);

        // Get the lineitem
        restLineitemMockMvc.perform(get("/api/lineitems/{id}", lineitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lineitem.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLineitem() throws Exception {
        // Get the lineitem
        restLineitemMockMvc.perform(get("/api/lineitems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineitem() throws Exception {
        // Initialize the database
        lineitemRepository.saveAndFlush(lineitem);

		int databaseSizeBeforeUpdate = lineitemRepository.findAll().size();

        // Update the lineitem
        lineitem.setAmount(UPDATED_AMOUNT);
        lineitem.setType(UPDATED_TYPE);

        restLineitemMockMvc.perform(put("/api/lineitems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineitem)))
                .andExpect(status().isOk());

        // Validate the Lineitem in the database
        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems).hasSize(databaseSizeBeforeUpdate);
        Lineitem testLineitem = lineitems.get(lineitems.size() - 1);
        assertThat(testLineitem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testLineitem.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteLineitem() throws Exception {
        // Initialize the database
        lineitemRepository.saveAndFlush(lineitem);

		int databaseSizeBeforeDelete = lineitemRepository.findAll().size();

        // Get the lineitem
        restLineitemMockMvc.perform(delete("/api/lineitems/{id}", lineitem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
