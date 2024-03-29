package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.config.WebMvcConfig;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.repositories.PositionRepository;
import com.marcomarchionni.strategistapi.repositories.StrategyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleUser;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(WebMvcConfig.class)
@Sql("classpath:dbScripts/insertSampleData.sql")
@Transactional
class PositionControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    StrategyRepository strategyRepository;

    User user;

    @BeforeEach
    void setup() {
        // Setup authenticated user for testing
        user = getSampleUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getPositionsInvalidEndpoint() throws Exception {

        mockMvc.perform(get("/pasitions"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type", is("endpoint-not-found")));
    }

    @ParameterizedTest
    @CsvSource({",ZM,,0", ",DIS,STK,1", "true,,,3"})
    void findByParamsSuccess(String tagged, String symbol, String assetCategory, int expectedSize) throws Exception {

        mockMvc.perform(get("/positions")
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)));
    }

    @ParameterizedTest
    @CsvSource({"farse,,", ",,GOLD"})
    void findByParamsBadRequest(String tagged, String symbol, String assetCategory) throws Exception {

        mockMvc.perform(get("/positions")
                        .param("tagged", tagged)
                        .param("symbol", symbol)
                        .param("assetCategory", assetCategory))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @ParameterizedTest
    @CsvSource({"ZM long,AAPL", "IBKR put,ADBE"})
    void updateStrategyIdSuccess(String strategyName, String expectedSymbol) throws Exception {
        Long strategyId = strategyRepository.findByAccountIdAndName(user.getAccountId(), strategyName).get().getId();
        Long positionId = positionRepository.findByAccountIdAndSymbol(user.getAccountId(), expectedSymbol).get()
                .getId();

        StrategyAssign positionUpdate = StrategyAssign.builder().id(positionId).strategyId(strategyId).build();

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(positionUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(expectedSymbol)))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(strategyId))));
    }


    @ParameterizedTest
    @CsvSource({"265598, 3455", "20, 1", ",,"})
    void updateStrategyIdExceptions(Long positionId, Long strategyId) throws Exception {

        StrategyAssign positionUpdate = StrategyAssign.builder().id(positionId).strategyId(strategyId).build();

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(positionUpdate)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").isNotEmpty());
    }

    @Test
    void updateStrategyIdEmptyBodyException() throws Exception {

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").isNotEmpty());
    }
}
