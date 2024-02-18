package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.request.UpdateName;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapper;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapperImpl;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.PortfolioService;
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
                .map(portfolioMapper::toPortfolioSummaryDto)
                .toList();

        // setup mock behavior
        when(portfolioService.findAll()).thenReturn(portfolioSummaries);

        // Execute test
        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(portfolioSummaries.size())));
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
    void createPortfolioSuccess() throws Exception {
        // setup test data
        PortfolioCreate portfolioCreate = PortfolioCreate.builder().name(userPortfolio.getName()).build();
        PortfolioDetail portfolioDetail = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.create(portfolioCreate)).thenReturn(portfolioDetail);

        // Execute test
        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(userPortfolio.getName())))
                .andExpect(jsonPath("$.id", is(userPortfolio.getId().intValue())))
                .andExpect(jsonPath("$.accountId", is(userPortfolio.getAccountId())));
    }

    @Test
    void createPortfolioInvalidNameException() throws Exception {
        // setup test data
        PortfolioCreate portfolioCreate = PortfolioCreate.builder().name("A").build();

        // execute test
        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(portfolioCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type", is("invalid-query-parameter")));
    }

    @Test
    void updatePortfolioName() throws Exception {
        // setup test
        UpdateName updateName = UpdateName.builder().id(userPortfolio.getId()).name("NewPortfolioName")
                .build();
        PortfolioDetail portfolioDetail = portfolioMapper.toPortfolioDetailDto(userPortfolio);

        // setup mock behavior
        when(portfolioService.updateName(updateName)).thenReturn(portfolioDetail);

        // Execute test
        mockMvc.perform(put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateName)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(portfolioDetail.getName())));
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