package com.marcomarchionni.ibportfolio.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioListDto;
import com.marcomarchionni.ibportfolio.models.mapping.PortfolioMapper;
import com.marcomarchionni.ibportfolio.models.mapping.PortfolioMapperImpl;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
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
    PortfolioMapper portfolioMapper;

    List<Portfolio> portfolios;
    List<PortfolioListDto> portfolioListDtos;
    Portfolio portfolio;
    PortfolioDetailDto portfolioDetailDto;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();
        portfolios = getSamplePortfolios();
        portfolioListDtos = portfolios.stream().map(portfolioMapper::toPortfolioListDto).collect(Collectors.toList());
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