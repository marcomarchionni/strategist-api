package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
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

    @ParameterizedTest
    @CsvSource({"ZM long,1", ",7"})
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
        Long strategyId = strategyRepository.findByName(expectedName).get(0).getId();

        mockMvc.perform(get("/strategies/{id}", strategyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(expectedName)))
                .andExpect(jsonPath("$.trades", not(empty())));
    }

    @Test
    void createSuccess() throws Exception {
        Long portfolioId = portfolioRepository.findByName("Saver Portfolio").get(0).getId();
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("AAPL long").portfolioId(portfolioId).build();

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
        Long strategyId = strategyRepository.findByName("ZM long").get(0).getId();

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
        Long strategyId = strategyRepository.findByName("IRBT long").get(0).getId();
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