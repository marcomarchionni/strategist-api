package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.mappers.DividendMapper;
import com.marcomarchionni.strategistapi.mappers.DividendMapperImpl;
import com.marcomarchionni.strategistapi.services.DividendService;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DividendController.class)
@AutoConfigureMockMvc(addFilters = false)
class DividendControllerTest {

    @MockBean
    DividendService dividendService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    DividendMapper dividendMapper;
    List<DividendSummary> dividendSummaryList;
    DividendSummary dividendSummary;
    User user;

    @BeforeEach
    void setUp() {
        // setup mapper
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        dividendMapper = new DividendMapperImpl(mapper);

        //setup data
        user = getSampleUser();
        dividendSummaryList = TestUtils.getSampleDividends()
                .stream()
                .map(dividendMapper::toDividendListDto)
                .toList();
        dividendSummary = dividendMapper.toDividendListDto(getSampleClosedDividend());

        //setup mocks
        when(userService.getAuthenticatedUser()).thenReturn(user);
    }

    @Test
    void getWithParameters() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendSummaryList);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendSummaryList.size())));
    }

    @Test
    void getWithParametersException() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendSummaryList);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendSummaryList.size())));
    }

    @Test
    void updateStrategyId() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Strategy strategy = getSampleStrategy();
        Dividend dividend = getSampleClosedDividend();
        StrategyAssign dividendUpdate = StrategyAssign
                .builder()
                .id(dividend.getId())
                .strategyId(strategy.getId())
                .build();
        dividend.setStrategy(strategy);
        DividendSummary expectedDividendSummary = dividendMapper.toDividendListDto(dividend);

        when(dividendService.updateStrategyId(dividendUpdate)).thenReturn(expectedDividendSummary);

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dividendUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(dividend.getSymbol())))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(dividendUpdate.getStrategyId()))));
    }
}