package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategies;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StrategyControllerTest {

    MockMvc mockMvc;

    @Mock
    StrategyService strategyService;

    @InjectMocks
    StrategyController strategyController;

    ObjectMapper mapper;

    List<Strategy> strategies;
    Strategy strategy;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(strategyController).build();
        strategies = getSampleStrategies();
        strategy = getSampleStrategy();
    }

    @Test
    void findByParams() throws Exception {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();

        when(strategyService.findByParams(strategyFindDto)).thenReturn(strategies);

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFindDto.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(strategies.size())));
    }

    @Test
    void updateNameSuccess() throws Exception {

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("NewName").build();
        when(strategyService.updateName(updateNameDto)).thenReturn(strategy);

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
        when(strategyService.create(strategyCreateDto)).thenReturn(strategy);

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