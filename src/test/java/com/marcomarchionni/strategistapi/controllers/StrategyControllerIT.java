package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import com.marcomarchionni.strategistapi.repositories.StrategyRepository;
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
class StrategyControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StrategyRepository strategyRepository;

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

    @ParameterizedTest
    @CsvSource({"ZM long,1", ",7", "ADBE long,0"})
    void findByParamsSuccess(String strategyName, int expectedSize) throws Exception {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().name(strategyName).build();

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFindDto.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    void findByParamsException(String strategyName) throws Exception {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().name(strategyName).build();

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFindDto.getName()))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @ParameterizedTest
    @CsvSource({"ZM long", "IBKR put"})
    void findByIdSuccess(String expectedName) throws Exception {

        Optional<Strategy> strategy = strategyRepository.findByAccountIdAndName(user.getAccountId(), expectedName);
        assertTrue(strategy.isPresent());
        Long strategyId = strategy.get().getId();

        mockMvc.perform(get("/strategies/{id}", strategyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(expectedName)))
                .andExpect(jsonPath("$.trades", not(empty())));
    }

    @Test
    void createSuccess() throws Exception {
        Optional<Portfolio> portfolio = portfolioRepository.findByAccountIdAndName("U1111111", "Saver Portfolio");
        assertTrue(portfolio.isPresent());
        Long portfolioId = portfolio.get().getId();
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("AAPL long").portfolioId(portfolioId)
                .build();

        mockMvc.perform(post("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(strategyCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void updateNameSuccess() throws Exception {
        Long strategyId = strategyRepository.findByAccountIdAndName(user.getAccountId(), "ZM long").get().getId();

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(strategyId).name("ZM leap").build();

        mockMvc.perform(put("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(updateNameDto.getId()))))
                .andExpect(jsonPath("$.name", is(updateNameDto.getName())));
    }

    @Test
    void updateNameException() throws Exception {

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("12NewName").build();

        mockMvc.perform(put("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void deleteSuccess() throws Exception {
        Long strategyId = strategyRepository.findByAccountIdAndName(user.getAccountId(), "IRBT long").get().getId();
        mockMvc.perform(delete("/strategies/{id}", strategyId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteException() throws Exception {
        Long id = 12988347222L;
        mockMvc.perform(delete("/strategies/{id}", id))
                .andExpect(status().is4xxClientError());
    }
}