package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TradeControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @ParameterizedTest
    @CsvSource({",,,ZM,,1",",,,TTWO,STK,2",",2022-06-14,true,,,1"})
    void findByFilterSuccess(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory, int expectedSize) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,STK"})
//    @CsvSource({"pippo,,,,STK",",,farse,ZM,","1969-01-01,,,,,","2022-06-14,2022-06-13,,,,"})
    void findByFilterBadRequest(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @ParameterizedTest
    @CsvSource({"1180780161,3,ZM","1180785204,4,FVRR"})
    void updateStrategyIdSuccess(Long tradeId, Long strategyId, String expectedSymbol) throws Exception {

        UpdateStrategyDto tradeUpdate = UpdateStrategyDto.builder().id(tradeId).strategyId(strategyId).build();

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(tradeUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(expectedSymbol)))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategyId))));
    }

    @ParameterizedTest
    @CsvSource({"1180780161, 20", "20, 1", ",,"})
    void updateStrategyIdExceptions(Long tradeId, Long strategyId) throws Exception {

        UpdateStrategyDto tradeUpdate = UpdateStrategyDto.builder().id(tradeId).strategyId(strategyId).build();

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(tradeUpdate)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBody() throws Exception {

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }
}
