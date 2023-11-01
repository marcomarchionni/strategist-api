package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapper;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapperImpl;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StrategyController.class)
class StrategyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StrategyService strategyService;

    ObjectMapper mapper = new ObjectMapper();
    StrategyMapper strategyMapper = new StrategyMapperImpl(new ModelMapper());
    List<Strategy> strategies = getSampleStrategies();
    List<StrategySummaryDto> strategySummaryDtos;
    StrategyDetailDto strategyDetailDto;
    Strategy strategy = getSampleStrategy();

    @BeforeEach
    void setUp() {
        strategySummaryDtos = strategies.stream().map(strategyMapper::toStrategyListDto).toList();
        strategyDetailDto = strategyMapper.toStrategyDetailDto(strategy);
    }

    @Test
    void findByParams() throws Exception {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();

        when(strategyService.findByFilter(strategyFindDto)).thenReturn(strategySummaryDtos);

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFindDto.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(strategies.size())));
    }

    @Test
    void findById() throws Exception {
        Long id = 1L;
        Strategy strategy1 = getSampleStrategy();
        strategy1.getTrades().add(getSampleTrade());
        strategy1.getPositions().add(getSamplePosition());
        strategyDetailDto = strategyMapper.toStrategyDetailDto(strategy1);
        System.out.println(strategyDetailDto);

        when(strategyService.findById(id)).thenReturn(strategyDetailDto);

        mockMvc.perform(get("/strategies/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategyDetailDto.getName())))
                .andExpect(jsonPath("$.trades", hasSize(1)));
    }

    @Test
    void updateNameSuccess() throws Exception {

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("NewName").build();
        when(strategyService.updateName(updateNameDto)).thenReturn(strategyDetailDto);

        mockMvc.perform(put("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(strategy.getId()))));
    }

    @Test
    void createSuccess() throws Exception {
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("ZM long").portfolioId(1L).build();
//        StrategyListDto strategyListDto = StrategyListDto.builder().id(1L).name("ZM long").portfolioId(1L).portfolioName("Saver").build();
        when(strategyService.create(strategyCreateDto)).thenReturn(strategyDetailDto);

        mockMvc.perform(post("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(strategyCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategy.getName())));
    }

    @Test
    void deleteSuccess() throws Exception {
        doNothing().when(strategyService).deleteById(strategy.getId());

        mockMvc.perform(delete("/strategies/{id}", strategy.getId()))
                .andExpect(status().isOk());
    }
}