package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class PortfolioControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    ObjectMapper mapper;

    Portfolio portfolio;

    @BeforeEach
    void setup() {
        portfolio = getSamplePortfolio("MF StockAdvisor");
    }

    @Test
    void findPortfolios() throws Exception {
        int expectedSize = portfolioRepository.findAll().size();

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"1,3","2,2"})
    void findPortfolioSuccess(Long id, int expectedSize) throws Exception {

        mockMvc.perform(get("/portfolios/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(id))))
                .andExpect(jsonPath("$.strategies", hasSize(expectedSize)));
    }

    @Test
    void createPortfolioSuccess() throws Exception {

        mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(portfolio)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void updatePortfolioNameSuccess() throws Exception {

        Portfolio commandPortfolio = Portfolio.builder().id(1L).portfolioName("SuperSaver").build();

        mockMvc.perform(put("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandPortfolio)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.portfolioName", is(commandPortfolio.getPortfolioName())));
    }

    @Test
    void findById() {
        Optional<Portfolio> portfolio = portfolioRepository.findById(1L);
        assertTrue(portfolio.isPresent());
    }

    @Test
    void deleteByIdException() throws Exception {
        Long portfolioId = 1L;
        assertTrue(portfolioRepository.findById(portfolioId).isPresent());

        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        Long portfolioId = 3L;
        assertTrue(portfolioRepository.findById(portfolioId).isPresent());

        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(portfolioRepository.findById(portfolioId).isEmpty());
    }
}