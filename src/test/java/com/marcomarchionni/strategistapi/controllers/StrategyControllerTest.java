package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.mappers.StrategyMapper;
import com.marcomarchionni.strategistapi.mappers.StrategyMapperImpl;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.StrategyService;
import com.marcomarchionni.strategistapi.services.UserService;
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
        List<StrategySummary> strategySummaries = userStrategies
                .stream()
                .map(strategyMapper::toStrategySummaryDto)
                .toList();
        StrategyFind strategyFind = StrategyFind.builder().build();

        // mock service calls
        when(strategyService.findByFilter(strategyFind)).thenReturn(strategySummaries);

        mockMvc.perform(get("/strategies")
                        .param("name", strategyFind.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(userStrategies.size())))
                .andExpect(jsonPath("$[0].accountId", is(strategySummaries.get(0).getAccountId())));
    }

    @Test
    void findById() throws Exception {
        // setup test data
        userStrategy.getTrades().add(getSampleTrade());
        userStrategy.getPositions().add(getSamplePosition());
        Long strategyId = userStrategy.getId();
        StrategyDetail strategyDetail = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.findById(strategyId)).thenReturn(strategyDetail);

        mockMvc.perform(get("/strategies/{id}", strategyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategyDetail.getName())))
                .andExpect(jsonPath("$.trades", hasSize(1)))
                .andExpect(jsonPath("$.positions", hasSize(1)));
    }

    @Test
    void updateNameSuccess() throws Exception {
        // setup test data
        Long strategyId = userStrategy.getId();
        NameUpdate nameUpdate = NameUpdate.builder().id(strategyId).name("NewName").build();
        userStrategy.setName(nameUpdate.getName());
        StrategyDetail strategyDetail = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.updateName(nameUpdate)).thenReturn(strategyDetail);

        // execute
        mockMvc.perform(put("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nameUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(strategyId))))
                .andExpect(jsonPath("$.name", is(nameUpdate.getName())))
                .andExpect(jsonPath("$.accountId", is(user.getAccountId())));
    }

    @Test
    void createSuccess() throws Exception {
        // setup test data
        Portfolio userPortfolio = getSamplePortfolio("Saver");
        userStrategy.setPortfolio(userPortfolio);
        StrategyCreate strategyCreate = StrategyCreate.builder()
                .name(userStrategy.getName())
                .portfolioId(userPortfolio.getId())
                .build();
        StrategyDetail strategyDetail = strategyMapper.toStrategyDetailDto(userStrategy);

        // mock service calls
        when(strategyService.create(strategyCreate)).thenReturn(strategyDetail);

        // execute
        mockMvc.perform(post("/strategies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(strategyCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(strategyCreate.getName())))
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
        verify(strategyService, times(1)).deleteById(strategyId);
    }
}