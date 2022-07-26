package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.services.TradeService;
import com.marcomarchionni.ibportfolio.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    TradeService tradeService;

    @InjectMocks
    TradeController tradeController;

    ObjectMapper mapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void getTrades() throws Exception {
        List<Trade> trades = TestUtils.getSampleTrades();

        when(tradeService.findWithParameters(any(), any(), any(), any(), any())).thenReturn(trades);

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(trades.size())));
    }

    @ParameterizedTest
    @CsvSource({",,,ZM,",",2022-06-14,true,,"})
    void getTradesWithParameters(String startDate, String endDate, String tagged, String symbol, String assetCategory) throws Exception {

        List<Trade> resultList = TestUtils.getSampleTrades();

        when(tradeService.findWithParameters(any(),any(),any(),any(),any())).thenReturn(resultList);

        mockMvc.perform(get("/trades")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(resultList.size())));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,",",,farse,ZM,","1200-01-01,1350-02-12,,,"})
    void getTradesWithParametersBadRequest(String startDate, String endDate, String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateStrategyIdTest() throws Exception {

        Trade trade = TestUtils.getSampleTrade();

        when(tradeService.updateStrategyId(trade)).thenReturn(trade);

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(trade.getSymbol())));
    }
}