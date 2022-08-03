package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;
import com.marcomarchionni.ibportfolio.models.mapping.TradeMapper;
import com.marcomarchionni.ibportfolio.models.mapping.TradeMapperImpl;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrade;
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

    ObjectMapper objectMapper;

    TradeMapper tradeMapper;

    MockMvc mockMvc;

    List<TradeListDto> tradeListDtos;
    Trade trade;
    Strategy strategy;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        tradeMapper = new TradeMapperImpl(new ModelMapper());
        tradeListDtos = TestUtils.getSampleTrades()
                .stream().map(tradeMapper::toTradeListDto).collect(Collectors.toList());
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
        trade = getSampleTrade();
        strategy = getSampleStrategy();
    }

    @Test
    void getTrades() throws Exception {

        when(tradeService.findByParams(any())).thenReturn(tradeListDtos);

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tradeListDtos.size())));
    }

    @ParameterizedTest
    @CsvSource({",,,ZM,",",2022-06-14,true,,"})
    void findTradesSuccess(String tradeDateFrom, String tradeDateTo, String tagged, String symbol, String assetCategory) throws Exception {

        when(tradeService.findByParams(any())).thenReturn(tradeListDtos);

        mockMvc.perform(get("/trades")
                        .param("tradeDateFrom", tradeDateFrom)
                        .param("tradeDateTo", tradeDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tradeListDtos.size())));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,",",,farse,ZM,","1200-01-01,1350-02-12,,,"})
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
        TradeListDto tradeListDto = tradeMapper.toTradeListDto(trade);

        when(tradeService.updateStrategyId(tradeUpdate)).thenReturn(tradeListDto);

        mockMvc.perform(put("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(trade.getId()))))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategy.getId()))));
    }
}