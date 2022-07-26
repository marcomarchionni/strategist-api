package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrade;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
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
    void getTradesWithParameters(String startDate, String endDate, String tagged, String symbol, String assetCategory, int expectedSize) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("symbol", symbol)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("tagged", tagged)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,STK",",,farse,ZM,","1969-01-01,,,,,"})
    void getTradesWithParametersBadRequest(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @ParameterizedTest
    @CsvSource({"1180780161,3,ZM","1180785204,4,FVRR"})
    void updateStrategyIdSuccess(Long tradeId, Long strategyId, String expectedSymbol) throws Exception {

        Trade tradeCommand = Trade.builder().id(tradeId).strategyId(strategyId).build();

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(tradeCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(expectedSymbol)))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategyId))));
    }

    @Test
    void updateStrategyIdTest() throws Exception {

        Trade trade = getSampleTrade();

        mockMvc.perform(put("/trades")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is("ZM")));
    }

    @ParameterizedTest
    @CsvSource({"1180780161, 20", "20, 1", ",,"})
    void updateStrategyIdExceptions(Long tradeId, Long strategyId) throws Exception {

        Long invalidTradeId = 20L;
        assertFalse(tradeRepository.findById(invalidTradeId).isPresent());
        Long validStrategyId = getSampleStrategy().getId();
        assertTrue(strategyRepository.findById(validStrategyId).isPresent());

        Trade requestTrade = Trade.builder()
                .id(invalidTradeId)
                .strategyId(validStrategyId)
                .build();

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestTrade)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBody() throws Exception {

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
