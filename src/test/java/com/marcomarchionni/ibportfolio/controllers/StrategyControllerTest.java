package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapper;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapperImpl;
import com.marcomarchionni.ibportfolio.services.JwtService;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import com.marcomarchionni.ibportfolio.services.UserService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StrategyController.class)
@AutoConfigureMockMvc(addFilters = false)
class StrategyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StrategyService strategyService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;
    ObjectMapper mapper = new ObjectMapper();
    StrategyMapper strategyMapper = new StrategyMapperImpl(new ModelMapper());
    User user;
    Strategy userStrategy;

    @BeforeEach
    void setup() {
        user = getSampleUser();
        userStrategy = getSampleStrategy();

        // mock service calls
        when(userService.getAuthenticatedUser()).thenReturn(user);
    }

    @Test
    void findByParams() throws Exception {
        // setup test data
        List<Strategy> userStrategies = getSampleStrategies();
        List<StrategySummaryDto> strategySummaryDtos = userStrategies
                .stream()
                .map(strategyMapper::toStrategySummaryDto)
                .toList();
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();

        // mock service calls
        when(strategyService.findByFilter(user, strategyFindDto)).thenReturn(strategySummaryDtos);

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFindDto.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(userStrategies.size())))
                .andExpect(jsonPath("$[0].accountId", is(strategySummaryDtos.get(0).getAccountId())));
    }

    @Test
    void findById() throws Exception {
        // setup test data
        userStrategy.getTrades().add(getSampleTrade());
        userStrategy.getPositions().add(getSamplePosition());
        Long strategyId = userStrategy.getId();
        StrategyDetailDto strategyDetailDto = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.findByUserAndId(user, strategyId)).thenReturn(strategyDetailDto);

        mockMvc.perform(get("/strategies/{id}", strategyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategyDetailDto.getName())))
                .andExpect(jsonPath("$.trades", hasSize(1)))
                .andExpect(jsonPath("$.positions", hasSize(1)));
    }

    @Test
    void updateNameSuccess() throws Exception {
        // setup test data
        Long strategyId = userStrategy.getId();
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(strategyId).name("NewName").build();
        userStrategy.setName(updateNameDto.getName());
        StrategyDetailDto strategyDetailDto = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.updateName(user, updateNameDto)).thenReturn(strategyDetailDto);

        // execute
        mockMvc.perform(put("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(strategyId))))
                .andExpect(jsonPath("$.name", is(updateNameDto.getName())))
                .andExpect(jsonPath("$.accountId", is(user.getAccountId())));
    }

    @Test
    void createSuccess() throws Exception {
        // setup test data
        Portfolio userPortfolio = getSamplePortfolio("Saver");
        userStrategy.setPortfolio(userPortfolio);
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder()
                .name(userStrategy.getName())
                .portfolioId(userPortfolio.getId())
                .build();
        StrategyDetailDto strategyDetailDto = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.create(user, strategyCreateDto)).thenReturn(strategyDetailDto);

        // execute
        mockMvc.perform(post("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(strategyCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategyCreateDto.getName())))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(userStrategy.getId()))))
                .andExpect(jsonPath("$.accountId", is(user.getAccountId())))
                .andExpect(jsonPath("$.portfolioId", is(Math.toIntExact(userPortfolio.getId()))));
    }

    @Test
    void deleteSuccess() throws Exception {
        // setup test data
        Long strategyId = userStrategy.getId();

        mockMvc.perform(delete("/strategies/{id}", strategyId))
                .andDo(print())
                .andExpect(status().isOk());

        // verify that the service method was called
        verify(strategyService, times(1)).deleteByUserAndId(user, strategyId);
    }
}