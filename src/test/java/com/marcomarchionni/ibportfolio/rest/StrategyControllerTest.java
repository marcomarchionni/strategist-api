package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;
import com.marcomarchionni.ibportfolio.models.mapping.StrategyMapper;
import com.marcomarchionni.ibportfolio.models.mapping.StrategyMapperImpl;
import com.marcomarchionni.ibportfolio.services.StrategyService;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    StrategyMapper strategyMapper;

    List<Strategy> strategies;
    List<StrategyListDto> strategyListDtos;
    StrategyDetailDto strategyDetailDto;
    Strategy strategy;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(strategyController).build();
        strategies = getSampleStrategies();
        strategy = getSampleStrategy();
        strategyListDtos = strategies.stream().map(strategyMapper::toStrategyListDto).collect(Collectors.toList());
        strategyDetailDto = strategyMapper.toStrategyDetailDto(strategy);
    }

    @Test
    void findByParams() throws Exception {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();

        when(strategyService.findByParams(strategyFindDto)).thenReturn(strategyListDtos);

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