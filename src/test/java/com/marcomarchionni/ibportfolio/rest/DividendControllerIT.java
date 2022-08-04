package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    @CsvSource({"2022-06-01,,,,,,4",",,2022-07-01,2022-07-15,,FDX,1",",,,,true,,1"})
    void findDividendsSuccess(String exDateFrom,
                              String exDateTo,
                              String payDateFrom,
                              String payDateTo,
                              String tagged,
                              String symbol,
                              int expectedSize) throws Exception {

        mockMvc.perform(get("/dividends")
                        .param("exDateFrom", exDateFrom)
                        .param("exDateTo", exDateTo)
                        .param("payDateFrom", payDateFrom)
                        .param("payDateTo", payDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"pippo,,,,,",",,,,farse,",",,2022-06-02,2022-06-01,,,"})
    void findDividendsBadRequest(String exDateFrom,
                                 String exDateTo,
                                 String payDateFrom,
                                 String payDateTo,
                                 String tagged,
                                 String symbol) throws Exception {

        mockMvc.perform(get("/dividends")
                        .param("exDateFrom", exDateFrom)
                        .param("exDateTo", exDateTo)
                        .param("payDateFrom", payDateFrom)
                        .param("payDateTo", payDateTo)
                        .param("tagged", tagged)
                        .param("symbol", symbol))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @ParameterizedTest
    @CsvSource({"1029120220603,3,NKE","26754720220519,4,CGNX"})
    void updateStrategyIdSuccess(Long dividendId, Long strategyId, String expectedSymbol) throws Exception {

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
    @CsvSource({"1029120220603, 20", "20, 1", ",,"})
    void updateStrategyIdExceptions(Long dividendId, Long strategyId) throws Exception {

        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder().id(dividendId).strategyId(strategyId).build();

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dividendUpdate)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBody() throws Exception {

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
