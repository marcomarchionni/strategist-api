package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.services.PositionService;
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

    List<Position> positions;

    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
        mapper = new ObjectMapper();
        positions = getSamplePositions();
    }

    @Test
    void getPositions() throws Exception {

        when(positionService.findWithCriteria(any())).thenReturn(positions);

        mockMvc.perform(get("/positions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(positions.size())));
    }

    @Test
    void updateStrategy() throws Exception {

        Position position = getSamplePosition();
        Long positionId = position.getId();
        Position commandPosition = Position.builder().id(positionId).strategyId(2L).build();
        when(positionService.updateStrategyId(any())).thenReturn(position);

        mockMvc.perform(put("/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandPosition)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(positionId.intValue())));
    }

    @Test
    void updateStrategyNullBody() throws Exception {

        mockMvc.perform(put("/positions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}