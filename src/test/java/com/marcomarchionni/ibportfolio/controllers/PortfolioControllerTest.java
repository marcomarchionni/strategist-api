package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapperImpl;
import com.marcomarchionni.ibportfolio.services.JwtService;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(PortfolioController.class)
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @MockBean
    PortfolioService portfolioService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    ObjectMapper mapper;
    PortfolioMapper portfolioMapper;
    List<PortfolioSummaryDto> portfolioSummaryDtos;
    Portfolio portfolio;
    PortfolioDetailDto portfolioDetailDto;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        portfolioSummaryDtos = getSamplePortfolios()
                .stream()
                .map(portfolioMapper::toPortfolioListDto)
                .toList();
        portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolioDetailDto = portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Test
    void findPortfolios() throws Exception {
        User user = getSampleUser();
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(portfolioService.findAllByUser(user)).thenReturn(portfolioSummaryDtos);

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(portfolioSummaryDtos.size())));
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
    void findPortfolioException() throws Exception {
        when(portfolioService.findById(any())).thenThrow(new EntityNotFoundException("Portfolio with id: 1 not found"));

        mockMvc.perform(get("/portfolios/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void createPortfolioSuccess() throws Exception {

        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name(portfolio.getName()).build();
        when(userService.getAuthenticatedUser()).thenReturn(getSampleUser());
        when(portfolioService.create(any(User.class), any(PortfolioCreateDto.class))).thenReturn(portfolioDetailDto);

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolio.getName())));
    }

    @Test
    void createPortfolioInvalidNameException() throws Exception {

        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name("A").build();

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void updatePortfolioName() throws Exception {

        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("MFStockAdvisor").build();
        when(userService.getAuthenticatedUser()).thenReturn(getSampleUser());
        when(portfolioService.updateName(any(User.class), any(UpdateNameDto.class))).thenReturn(portfolioDetailDto);

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