package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolios;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PortfolioControllerTest {

    @Mock
    PortfolioService portfolioService;

    @InjectMocks
    PortfolioController portfolioController;

    MockMvc mockMvc;

    ObjectMapper mapper;

    final List<Portfolio> portfolios = getSamplePortfolios();
    final Portfolio portfolio = getSamplePortfolio("MFStockAdvisor");

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();
    }

    @Test
    void findPortfolios() throws Exception {
        when(portfolioService.findAll()).thenReturn(portfolios);

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(portfolios.size())));
    }

    @Test
    void findPortfolioSuccess() throws Exception {
        when(portfolioService.findById(any())).thenReturn(portfolio);

        mockMvc.perform(get("/portfolios/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.portfolioName", is(portfolio.getPortfolioName())));
    }

    @Test
    void createPortfolioSuccess() throws Exception {

        when(portfolioService.save(any())).thenReturn(portfolio);

        mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(portfolio)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.portfolioName", is(portfolio.getPortfolioName())));
    }


    @Test
    void updatePortfolioName() throws Exception {

        Portfolio portfolioCommand = Portfolio.builder().id(1L).portfolioName("MFStockAdvisor").build();

        when(portfolioService.updatePortfolioName(any())).thenReturn(portfolio);

        mockMvc.perform(put("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(portfolioCommand)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.portfolioName", is(portfolio.getPortfolioName())));
    }

    @Test
    void deletePortfolioSuccess() throws Exception {
        Long id = 1L;
        doNothing().when(portfolioService).deleteById(id);

        mockMvc.perform(delete("/portfolios/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}