package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.util.TestUtils;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleClosedDividend;
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

    MockMvc mockMvc;
    ObjectMapper mapper;
    List<Dividend> dividends;
    Dividend dividend;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dividendController).build();
        dividends = TestUtils.getSampleDividends();
        dividend = getSampleClosedDividend();
        mapper = new ObjectMapper();
    }

    @Test
    void getWithParameters() throws Exception {

        when(dividendService.findWithCriteria(any())).thenReturn(dividends);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividends.size())));
    }

    @Test
    void getWithParametersException() throws Exception {

        when(dividendService.findWithCriteria(any())).thenReturn(dividends);

        mockMvc.perform(get("/dividends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(dividends.size())));
    }

    @Test
    void updateStrategyId() throws Exception {

        Dividend dividendToUpdate = Dividend.builder().id(dividend.getId()).strategyId(2L).build();
        dividend.setStrategyId(dividendToUpdate.getStrategyId());

        when(dividendService.updateStrategyId(dividendToUpdate)).thenReturn(dividend);

        mockMvc.perform(put("/dividends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dividendToUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is(dividend.getSymbol())))
                .andExpect(jsonPath("$.strategyId", is(Math.toIntExact(dividendToUpdate.getStrategyId()))));
    }
}