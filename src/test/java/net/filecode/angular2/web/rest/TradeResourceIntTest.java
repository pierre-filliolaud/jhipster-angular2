package net.filecode.angular2.web.rest;

import net.filecode.angular2.JhipsterApp;

import net.filecode.angular2.domain.Trade;
import net.filecode.angular2.repository.TradeRepository;
import net.filecode.angular2.service.TradeService;
import net.filecode.angular2.web.rest.errors.ExceptionTranslator;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TradeResource REST controller.
 *
 * @see TradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class TradeResourceIntTest {

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_FAMILY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTradeMockMvc;

    private Trade trade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TradeResource tradeResource = new TradeResource(tradeService);
        this.restTradeMockMvc = MockMvcBuilders.standaloneSetup(tradeResource)
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
    public static Trade createEntity() {
        Trade trade = new Trade()
                .productType(DEFAULT_PRODUCT_TYPE)
                .productFamily(DEFAULT_PRODUCT_FAMILY)
                .price(DEFAULT_PRICE)
                .currency(DEFAULT_CURRENCY)
                .creationDate(DEFAULT_CREATION_DATE);
        return trade;
    }

    @Before
    public void initTest() {
        tradeRepository.deleteAll();
        trade = createEntity();
    }

    @Test
    public void createTrade() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade

        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate + 1);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testTrade.getProductFamily()).isEqualTo(DEFAULT_PRODUCT_FAMILY);
        assertThat(testTrade.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTrade.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testTrade.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    public void createTradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade with an existing ID
        Trade existingTrade = new Trade();
        existingTrade.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTrade)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTrades() throws Exception {
        // Initialize the database
        tradeRepository.save(trade);

        // Get all the tradeList
        restTradeMockMvc.perform(get("/api/trades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productFamily").value(hasItem(DEFAULT_PRODUCT_FAMILY.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    public void getTrade() throws Exception {
        // Initialize the database
        tradeRepository.save(trade);

        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trade.getId()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.productFamily").value(DEFAULT_PRODUCT_FAMILY.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    public void getNonExistingTrade() throws Exception {
        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrade() throws Exception {
        // Initialize the database
        tradeService.save(trade);

        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade
        Trade updatedTrade = tradeRepository.findOne(trade.getId());
        updatedTrade
                .productType(UPDATED_PRODUCT_TYPE)
                .productFamily(UPDATED_PRODUCT_FAMILY)
                .price(UPDATED_PRICE)
                .currency(UPDATED_CURRENCY)
                .creationDate(UPDATED_CREATION_DATE);

        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrade)))
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testTrade.getProductFamily()).isEqualTo(UPDATED_PRODUCT_FAMILY);
        assertThat(testTrade.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTrade.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testTrade.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    public void updateNonExistingTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Create the Trade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrade() throws Exception {
        // Initialize the database
        tradeService.save(trade);

        int databaseSizeBeforeDelete = tradeRepository.findAll().size();

        // Get the trade
        restTradeMockMvc.perform(delete("/api/trades/{id}", trade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trade.class);
    }
}
