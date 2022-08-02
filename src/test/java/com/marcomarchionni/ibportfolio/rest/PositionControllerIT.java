package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class PositionControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @ParameterizedTest
    @CsvSource({",ZM,,0",",DIS,STK,1","true,,,2"})
    void findByParamsSuccess(String tagged, String symbol, String assetCategory, int expectedSize) throws Exception {

        mockMvc.perform(get("/positions")
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"farse,,",",,GOLD"})
    void findByParamsBadRequest(String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/positions")
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @ParameterizedTest
    @CsvSource({"265598,3,AAPL","265768,4,ADBE"})
    void updateStrategyIdSuccess(Long positionId, Long strategyId, String expectedSymbol) throws Exception {

        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder().id(positionId).strategyId(strategyId).build();

        mockMvc.perform(put("/positions")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(positionUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(expectedSymbol)))
                .andExpect(jsonPath("$.strategy.id", is(Math.toIntExact(strategyId))));
    }


    @ParameterizedTest
    @CsvSource({"265598, 20", "20, 1", ",,"})
    void updateStrategyIdExceptions(Long positionId, Long strategyId) throws Exception {

        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder().id(positionId).strategyId(strategyId).build();

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(positionUpdate)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBodyException() throws Exception {

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
