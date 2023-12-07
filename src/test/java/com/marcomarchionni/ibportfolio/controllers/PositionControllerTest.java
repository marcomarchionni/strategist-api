package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.mappers.PositionMapperImpl;
import com.marcomarchionni.ibportfolio.services.JwtService;
import com.marcomarchionni.ibportfolio.services.PositionService;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePosition;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePositions;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PositionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PositionControllerTest {

    @MockBean
    PositionService positionService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;
    ObjectMapper mapper;
    PositionMapper positionMapper;

    List<Position> positions;
    List<PositionSummaryDto> positionSummaryDtos;
    Position position;
    PositionSummaryDto positionSummaryDto;

    @BeforeEach
    void setup() {
        positions = getSamplePositions();
        positionMapper = new PositionMapperImpl(new ModelMapper());
        positionSummaryDtos = positions
                .stream()
                .map(positionMapper::toPositionListDto)
                .toList();
        position = getSamplePosition();
        positionSummaryDto = positionMapper.toPositionListDto(position);
    }

    @Test
    void getPositions() throws Exception {

        when(positionService.findByFilter(any())).thenReturn(positionSummaryDtos);

        mockMvc.perform(get("/positions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(positionSummaryDtos.size())));
    }


    @Test
    void updateStrategy() throws Exception {

        mapper = new ObjectMapper();

        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder().id(position.getId()).strategyId(2L).build();
        when(positionService.updateStrategyId(any())).thenReturn(positionSummaryDto);

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(positionUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(position.getId()))));
    }

    @Test
    void updateStrategyNullBody() throws Exception {

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type", is("request-body-not-readable")));
    }
}