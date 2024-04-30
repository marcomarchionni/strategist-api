package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleUser;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
class PortfolioControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    ObjectMapper mapper;
    User user;

    @BeforeEach
    void setup() {
        // Setup authenticated user for testing
        user = getSampleUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void findAllSuccess() throws Exception {
        int expectedSize = portfolioRepository.findAllByAccountId(user.getAccountId()).size();

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"U1111111,Saver Portfolio,5", "U1111111,Trader Portfolio,2"})
    void findByIdSuccess(String accountId, String portfolioName, int expectedSize) throws Exception {
        Optional<Portfolio> portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        assertTrue(portfolio.isPresent());
        Long portfolioId = portfolio.get().getId();

        mockMvc.perform(get("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(portfolioId))))
                .andExpect(jsonPath("$.strategies", hasSize(expectedSize)));
    }

    @Test
    void createPortfolioSuccess() throws Exception {
        PortfolioSave portfolioSave = PortfolioSave.builder().name("Super Saver").build();

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioSave)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Super Saver")));
    }

    @ParameterizedTest
    @CsvSource({"U1111111, Super Portfolio", "U1111111, Marco's Portfolio", "U1111111, Zipp"})
    void updatePortfolioNameSuccess(String accountId, String portfolioName) throws Exception {
        Long portfolioId = portfolioRepository.findByAccountIdAndName(accountId, "Saver Portfolio").get().getId();
        NameUpdate nameUpdate = NameUpdate.builder().id(portfolioId).name(portfolioName).build();

        mockMvc.perform(put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nameUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(nameUpdate.getName())));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Saver Portfolio", ","})
    void createPortfolioException(String portfolioName) throws Exception {
        NameUpdate badNameUpdate = NameUpdate.builder().name(portfolioName).build();

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badNameUpdate)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        Optional<Portfolio> portfolio = portfolioRepository.findByAccountIdAndName("U1111111", "Millionaire Portfolio");
        assertTrue(portfolio.isPresent());
        Long portfolioId = portfolio.get().getId();

        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(portfolioRepository.findById(portfolioId).isEmpty());
    }

    @Test
    void deleteByIdUnableToDeleteEntitiesException() throws Exception {
        Optional<Portfolio> portfolio = portfolioRepository.findByAccountIdAndName("U1111111", "Saver Portfolio");
        assertTrue(portfolio.isPresent());
        Long portfolioId = portfolio.get().getId();

        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type", is("unable-to-delete-entities")));
    }
}