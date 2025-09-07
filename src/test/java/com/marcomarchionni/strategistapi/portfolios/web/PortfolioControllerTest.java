package com.marcomarchionni.strategistapi.portfolios.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.portfolios.mapper.PortfolioMapper;
import com.marcomarchionni.strategistapi.portfolios.mapper.PortfolioMapperImpl;
import com.marcomarchionni.strategistapi.portfolios.service.PortfolioService;
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

@WebMvcTest(PortfolioController.class)
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @MockBean
    PortfolioService portfolioService;

    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("unused")
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
        List<PortfolioSummary> portfolioSummaries = getSamplePortfolios()
                .stream()
                .peek(portfolio -> portfolio.setAccountId(accountId))
                .map(portfolioMapper::portfolioToPortfolioSummary)
                .toList();
        ApiResponse<PortfolioSummary> response = ApiResponse.<PortfolioSummary>builder()
                .result(portfolioSummaries)
                .count(portfolioSummaries.size())
                .build();

        // setup mock behavior
        when(portfolioService.findAllWithCount(any())).thenReturn(response);

        // Execute test
        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", hasSize(portfolioSummaries.size())))
                .andExpect(jsonPath("$.count", is(portfolioSummaries.size())));
    }

    @Test
    void findPortfoliosWithCount() throws Exception {
        // setup test data
        String accountId = user.getAccountId();
        List<PortfolioSummary> portfolioSummaries = getSamplePortfolios()
                .stream()
                .peek(portfolio -> portfolio.setAccountId(accountId))
                .map(portfolioMapper::portfolioToPortfolioSummary)
                .toList();
        ApiResponse<PortfolioSummary> response = ApiResponse.<PortfolioSummary>builder()
                .result(portfolioSummaries)
                .count(portfolioSummaries.size())
                .build();

        // setup mock behavior
        when(portfolioService.findAllWithCount(any())).thenReturn(response);

        // Execute test
        mockMvc.perform(get("/portfolios?skip=0&top=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", hasSize(portfolioSummaries.size())))
                .andExpect(jsonPath("$.count", is(portfolioSummaries.size())));
    }

    @Test
    void findPortfolioSuccess() throws Exception {
        // setup test data
        Long portfolioId = userPortfolio.getId();
        PortfolioDetail portfolioDetail = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.findById(portfolioId)).thenReturn(portfolioDetail);

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
