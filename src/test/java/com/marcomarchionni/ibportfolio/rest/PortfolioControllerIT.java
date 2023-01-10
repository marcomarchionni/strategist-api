package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    void findAllSuccess() throws Exception {
        int expectedSize = portfolioRepository.findAll().size();

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"1,4", "2,2"})
    void findByIdSuccess(Long id, int expectedSize) throws Exception {

        mockMvc.perform(get("/portfolios/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(id))))
                .andExpect(jsonPath("$.strategies", hasSize(expectedSize)));
    }

    @Test
    void createPortfolioSuccess() throws Exception {
        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name("Super Saver").build();

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Super Saver")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Super Portfolio", "Marco's Portfolio", "Zipp"})
    void updatePortfolioNameSuccess(String portfolioName) throws Exception {

        UpdateNameDto updateName = UpdateNameDto.builder().id(1L).name(portfolioName).build();

        mockMvc.perform(put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateName)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(updateName.getName())));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Saver Portfolio", ","})
    void createPortfolioException(String portfolioName) throws Exception {
        UpdateNameDto badUpdateName = UpdateNameDto.builder().name(portfolioName).build();

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badUpdateName)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
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

    @Test
    void deleteByIdException() throws Exception {
        Long portfolioId = 1L;
        assertTrue(portfolioRepository.findById(portfolioId).isPresent());

        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}