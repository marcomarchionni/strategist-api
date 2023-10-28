package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioListDto;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapperImpl;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(PortfolioController.class)
class PortfolioControllerTest {

    @MockBean
    PortfolioService portfolioService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper;
    PortfolioMapper portfolioMapper;
    List<PortfolioListDto> portfolioListDtos;
    Portfolio portfolio;
    PortfolioDetailDto portfolioDetailDto;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        portfolioListDtos = getSamplePortfolios()
                .stream()
                .map(portfolioMapper::toPortfolioListDto)
                .toList();
        portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolioDetailDto = portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Test
    void findPortfolios() throws Exception {
        when(portfolioService.findAll()).thenReturn(portfolioListDtos);

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(portfolioListDtos.size())));
    }

    @Test
    void findPortfolioSuccess() throws Exception {
        when(portfolioService.findById(any())).thenReturn(portfolioDetailDto);

        mockMvc.perform(get("/portfolios/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolio.getName())));
    }

    @Test
    void createPortfolioSuccess() throws Exception {

        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name(portfolio.getName()).build();
        when(portfolioService.create(any())).thenReturn(portfolioDetailDto);

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolio.getName())));
    }

    @Test
    void updatePortfolioName() throws Exception {

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("MFStockAdvisor").build();

        when(portfolioService.updateName(any())).thenReturn(portfolioDetailDto);

        mockMvc.perform(put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolioDetailDto.getName())));
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