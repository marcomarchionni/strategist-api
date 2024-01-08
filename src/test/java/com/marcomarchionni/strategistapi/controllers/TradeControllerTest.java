package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.TradeFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummaryDto;
import com.marcomarchionni.strategistapi.mappers.TradeMapper;
import com.marcomarchionni.strategistapi.mappers.TradeMapperImpl;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.TradeService;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleTrade;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
class TradeControllerTest {

    @MockBean
    TradeService tradeService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;
    ObjectMapper objectMapper = new ObjectMapper();
    TradeMapper tradeMapper = new TradeMapperImpl(new ModelMapper());
    Trade trade = getSampleTrade();
    Strategy strategy = getSampleStrategy();
    List<TradeSummaryDto> tradeSummaryDtos;

    User user;

    @BeforeEach
    void setUp() {
        user = TestUtils.getSampleUser();
        objectMapper.registerModule(new JavaTimeModule());
        tradeMapper = new TradeMapperImpl(new ModelMapper());
        tradeSummaryDtos = TestUtils.getSampleTrades()
                .stream()
                .map(tradeMapper::toTradeListDto)
                .toList();
        when(userService.getAuthenticatedUser()).thenReturn(user);
    }

    @Test
    void getTrades() throws Exception {

        when(tradeService.findByFilter(any(TradeFindDto.class))).thenReturn(tradeSummaryDtos);

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tradeSummaryDtos.size())));
    }

    @ParameterizedTest
    @CsvSource({",,,ZM,", ",2022-06-14,true,,"})
    void findTradesSuccess(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory) throws Exception {

        when(tradeService.findByFilter(any(TradeFindDto.class))).thenReturn(tradeSummaryDtos);

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tradeSummaryDtos.size())));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,", ",,farse,ZM,", "1200-01-01,1350-02-12,,,"})
    void findTradesBadRequest(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateStrategyIdTest() throws Exception {

        UpdateStrategyDto tradeUpdate = UpdateStrategyDto.builder().id(trade.getId()).strategyId(strategy.getId()).build();
        trade.setStrategy(strategy);
        TradeSummaryDto tradeSummaryDto = tradeMapper.toTradeListDto(trade);

        when(tradeService.updateStrategyId(tradeUpdate)).thenReturn(tradeSummaryDto);

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(trade.getId()))))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategy.getId()))));
    }
}