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

@WebMvcTest(PortfolioController.class)
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @MockBean
    PortfolioService portfolioService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    ObjectMapper mapper;
    PortfolioMapper portfolioMapper;
    Portfolio userPortfolio;
    User user;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());

        userPortfolio = getSamplePortfolio("MFStockAdvisor");
        user = getSampleUser();
    }

    @Test
    void findPortfolios() throws Exception {
        // setup test data
        String accountId = user.getAccountId();
        List<PortfolioSummaryDto> portfolioSummaryDtos = getSamplePortfolios()
                .stream()
                .peek(portfolio -> portfolio.setAccountId(accountId))
                .map(portfolioMapper::toPortfolioSummaryDto)
                .toList();

        // setup mock behavior
        when(portfolioService.findAll()).thenReturn(portfolioSummaryDtos);

        // Execute test
        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(portfolioSummaryDtos.size())));
    }

    @Test
    void findPortfolioSuccess() throws Exception {
        // setup test data
        Long portfolioId = userPortfolio.getId();
        PortfolioDetailDto portfolioDetailDto = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.findById(portfolioId)).thenReturn(portfolioDetailDto);

        // Execute test
        mockMvc.perform(get("/portfolios/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(userPortfolio.getName())))
                .andExpect(jsonPath("$.id", is(userPortfolio.getId().intValue())))
                .andExpect(jsonPath("$.accountId", is(userPortfolio.getAccountId())))
                .andExpect(jsonPath("$.strategies", hasSize(userPortfolio.getStrategies().size())));
    }

    @Test
    void findPortfolioException() throws Exception {
        // setup test data
        Long unknownPortfolioId = 3L;

        // setup mock behavior
        when(portfolioService.findById(unknownPortfolioId)).thenThrow(
                new EntityNotFoundException(Portfolio.class, unknownPortfolioId));

        // Execute test
        mockMvc.perform(get("/portfolios/{id}", unknownPortfolioId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void createPortfolioSuccess() throws Exception {
        // setup test data
        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name(userPortfolio.getName()).build();
        PortfolioDetailDto portfolioDetailDto = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.create(portfolioCreateDto)).thenReturn(portfolioDetailDto);

        // Execute test
        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(userPortfolio.getName())))
                .andExpect(jsonPath("$.id", is(userPortfolio.getId().intValue())))
                .andExpect(jsonPath("$.accountId", is(userPortfolio.getAccountId())));
    }

    @Test
    void createPortfolioInvalidNameException() throws Exception {
        // setup test data
        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name("A").build();

        // execute test
        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreateDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type", is("invalid-query-parameter")));
    }

    @Test
    void updatePortfolioName() throws Exception {
        // setup test
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(userPortfolio.getId()).name("NewPortfolioName")
                .build();
        PortfolioDetailDto portfolioDetailDto = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.updateName(updateNameDto)).thenReturn(portfolioDetailDto);

        // Execute test
        mockMvc.perform(put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateNameDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolioDetailDto.getName())));
    }

    @Test
    void deletePortfolioSuccess() throws Exception {
        // setup test data
        Long portfolioId = userPortfolio.getId();

        // setup mock behavior
        doNothing().when(portfolioService).deleteById(portfolioId);

        // Execute test
        mockMvc.perform(delete("/portfolios/{id}", portfolioId))
                .andDo(print())
                .andExpect(status().isOk());

        // Verify results
        verify(portfolioService, times(1)).deleteById(portfolioId);
    }
}