package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Trade;
import org.junit.jupiter.api.BeforeAll;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setId(1180780161L);
        trade.setTradeId(387679436L);
        trade.setConId(361181057L);
        trade.setStrategyId(3L);
        trade.setTradeDate(LocalDate.parse("2022-06-07"));
        trade.setSymbol("ZM");
        trade.setAssetCategory("STK");
        trade.setMultiplier(1);
        trade.setBuySell("BUY");
        trade.setQuantity(BigDecimal.valueOf(15));
        trade.setTradePrice(BigDecimal.valueOf(111.3300));
        trade.setTradeMoney(BigDecimal.valueOf(1669.9500));
    }

    @Test
    void getTrades() throws Exception {

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @ParameterizedTest
    @CsvSource({",,,ZM,1",",,,TTWO,2",",2022-06-14,true,,1"})
    void getTradesWithParameters(String startDate, String endDate, String tagged, String symbol, int expectedSize) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("symbol", symbol)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("tagged", tagged))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,",",,farse,ZM"})
    void getTradesWithParametersBadRequest(String startDate, String endDate, String tagged, String symbol) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("symbol", symbol)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("tagged", tagged))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void updateStrategyIdTest() throws Exception {


        mockMvc.perform(put("/trades")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is("ZM")));
    }

    @Test
    void updateStrategyIdStrategyNotFoundTest() throws Exception {

        trade.setStrategyId(20L);

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(trade)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Strategy with id: 20 not found")));
    }

    @Test
    void updateStrategyIdTradeNotFoundTest() throws Exception {

        trade.setId(20L);

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(trade)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Trade with id: 20 not found")));
    }
}