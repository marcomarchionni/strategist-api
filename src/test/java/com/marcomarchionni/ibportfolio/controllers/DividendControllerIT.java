package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
class DividendControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    DividendRepository dividendRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @ParameterizedTest
    @CsvSource({"2022-06-01,,,,,,2", ",,2022-07-01,2022-07-15,,FDX,1", ",,,,true,,1"})
    void findDividendsSuccess(String exDateFrom, String exDateTo, String payDateFrom, String payDateTo, String tagged
            , String symbol, int expectedSize) throws Exception {

        mockMvc.perform(get("/dividends")
                        .param("exDateFrom", exDateFrom)
                        .param("exDateTo", exDateTo)
                        .param("payDateFrom", payDateFrom)
                        .param("payDateTo", payDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,,", ",,,,farse,", ",,2022-06-02,2022-06-01,,,"})
    void findDividendsBadRequest(String exDateFrom, String exDateTo, String payDateFrom, String payDateTo,
                                 String tagged, String symbol) throws Exception {

        mockMvc.perform(get("/dividends")
                        .param("exDateFrom", exDateFrom)
                        .param("exDateTo", exDateTo)
                        .param("payDateFrom", payDateFrom)
                        .param("payDateTo", payDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.type", is("invalid-query-parameter")));
    }

    @ParameterizedTest
    @CsvSource({"510058320220711,ZM long,FDX", "26754720220603,IBKR put,CGNX"})
    void updateStrategyIdSuccess(Long dividendId, String strategyName, String expectedSymbol) throws Exception {

        Long strategyId = strategyRepository.findByName(strategyName).get(0).getId();

        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder().id(dividendId).strategyId(strategyId).build();

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dividendUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(expectedSymbol)))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategyId))));
    }

    @ParameterizedTest
    @CsvSource({"1029120220603, 20", "20, 1", ",,", "\"r\",1"})
    void updateStrategyIdExceptions(String dividendId, String strategyId) throws Exception {

        String payload = String.format("{\"id\": %s, \"strategyId\": %s}", dividendId, strategyId);

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBody() throws Exception {

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }
}
