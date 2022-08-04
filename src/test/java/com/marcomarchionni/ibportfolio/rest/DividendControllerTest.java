package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapper;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapperImpl;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleClosedDividend;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DividendControllerTest {

    @Mock
    DividendService dividendService;

    @InjectMocks
    DividendController dividendController;

    DividendMapper dividendMapper;

    MockMvc mockMvc;
    ObjectMapper mapper;
    List<Dividend> dividends;
    List<DividendListDto> dividendListDtos;
    Dividend dividend;
    DividendListDto dividendListDto;
    Strategy strategy;

    @BeforeEach
    void setUp() {
        dividendMapper = new DividendMapperImpl(new ModelMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(dividendController).build();
        dividends = TestUtils.getSampleDividends();
        dividendListDtos = dividends.stream().map(dividendMapper::toDividendListDto).collect(Collectors.toList());
        dividend = getSampleClosedDividend();
        dividendListDto = dividendMapper.toDividendListDto(dividend);
        strategy = getSampleStrategy();
        mapper = new ObjectMapper();
    }

    @Test
    void getWithParameters() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendListDtos);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendListDtos.size())));
    }

    @Test
    void getWithParametersException() throws Exception {

        when(dividendService.findByFilter(any())).thenReturn(dividendListDtos);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividendListDtos.size())));
    }

    @Test
    void updateStrategyId() throws Exception {

        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder()
                .id(dividend.getId()).strategyId(strategy.getId()).build();
        dividend.setStrategy(strategy);
        DividendListDto expectedDividendListDto = dividendMapper.toDividendListDto(dividend);

        when(dividendService.updateStrategyId(dividendUpdate)).thenReturn(expectedDividendListDto);

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