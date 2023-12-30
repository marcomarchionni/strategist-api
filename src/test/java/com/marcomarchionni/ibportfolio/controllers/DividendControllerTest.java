package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.mappers.DividendMapperImpl;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.JwtService;
import com.marcomarchionni.ibportfolio.services.UserService;
import com.marcomarchionni.ibportfolio.util.TestUtils;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
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
    List<DividendSummaryDto> dividendSummaryDtos;
    DividendSummaryDto dividendSummaryDto;
    User user;

    @BeforeEach
    void setUp() {
        // setup mapper
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        dividendMapper = new DividendMapperImpl(mapper);

        //setup data
        user = getSampleUser();
        dividendSummaryDtos = TestUtils.getSampleDividends()
                .stream()
                .map(dividendMapper::toDividendListDto)
                .toList();
        dividendSummaryDto = dividendMapper.toDividendListDto(getSampleClosedDividend());

        //setup mocks
        when(userService.getAuthenticatedUser()).thenReturn(user);
    }

    @Test
    void getWithParameters() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendSummaryDtos);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendSummaryDtos.size())));
    }

    @Test
    void getWithParametersException() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendSummaryDtos);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendSummaryDtos.size())));
    }

    @Test
    void updateStrategyId() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Strategy strategy = getSampleStrategy();
        Dividend dividend = getSampleClosedDividend();
        UpdateStrategyDto dividendUpdate = UpdateStrategyDto
                .builder()
                .id(dividend.getId())
                .strategyId(strategy.getId())
                .build();
        dividend.setStrategy(strategy);
        DividendSummaryDto expectedDividendSummaryDto = dividendMapper.toDividendListDto(dividend);

        when(dividendService.updateStrategyId(dividendUpdate)).thenReturn(expectedDividendSummaryDto);

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