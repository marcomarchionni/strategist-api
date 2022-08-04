package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapper;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapperImpl;
import com.marcomarchionni.ibportfolio.services.PositionService;
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

@ExtendWith(MockitoExtension.class)
class PositionControllerTest {

    @Mock
    PositionService positionService;

    @InjectMocks
    PositionController positionController;

    MockMvc mockMvc;
    ObjectMapper mapper;
    PositionMapper positionMapper;

    List<Position> positions;
    List<PositionListDto> positionListDtos;
    Position position;
    PositionListDto positionListDto;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
        mapper = new ObjectMapper();
        positionMapper = new PositionMapperImpl(new ModelMapper());
        positions = getSamplePositions();
        positionListDtos = positions.stream().map(positionMapper::toPositionListDto)
                .collect(Collectors.toList());
        position = getSamplePosition();
        positionListDto = positionMapper.toPositionListDto(position);
    }

    @Test
    void getPositions() throws Exception {

        when(positionService.findByFilter(any())).thenReturn(positionListDtos);

        mockMvc.perform(get("/positions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(positionListDtos.size())));
    }

    @Test
    void updateStrategy() throws Exception {

        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder().id(position.getId()).strategyId(2L).build();
        when(positionService.updateStrategyId(any())).thenReturn(positionListDto);

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
                .andExpect(status().is4xxClientError());
    }
}