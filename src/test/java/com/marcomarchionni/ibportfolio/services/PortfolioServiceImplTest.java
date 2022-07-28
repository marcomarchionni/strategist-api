package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolios;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    PortfolioRepository portfolioRepository;

    @InjectMocks
    PortfolioServiceImpl portfolioService;

    List<Portfolio> portfolios;
    Portfolio portfolio;

    @BeforeEach
    void setup() {
        portfolios = getSamplePortfolios();
        portfolio = getSamplePortfolio("MF StockAdvisor");
    }

    @Test
    void findAll() {
        when(portfolioRepository.findAll()).thenReturn(portfolios);
        List<Portfolio> actualPortfolios = portfolioService.findAll();

        assertEquals(actualPortfolios.size(), portfolios.size());
    }

    @Test
    void findByIdSuccess() {
        when(portfolioRepository.findById(any())).thenReturn(Optional.of(portfolio));

        Portfolio actualPortfolio = portfolioService.findById(1L);
        assertEquals(actualPortfolio, portfolio);
    }

    @Test
    void findByIdException() {
        when(portfolioRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> portfolioService.findById(1L));
    }

    @Test
    void updatePortfolioNameSuccess() {
        Portfolio commandPortfolio = Portfolio.builder().id(1L).portfolioName("NewName").build();
        Portfolio savedPortfolio = Portfolio.builder().id(1L).portfolioName("OldName").build();

        when(portfolioRepository.findById(any())).thenReturn(Optional.of(savedPortfolio));
        when(portfolioRepository.save(commandPortfolio)).thenReturn(commandPortfolio);

        Portfolio updatedPortfolio = portfolioService.updatePortfolioName(commandPortfolio);
        assertEquals(updatedPortfolio.getId(), commandPortfolio.getId());
        assertEquals(updatedPortfolio.getPortfolioName(), commandPortfolio.getPortfolioName());
    }

    @Test
    void deleteByIdSuccess(){
        portfolio.setId(1L);
        portfolio.setStrategies(new ArrayList<>());
        when(portfolioRepository.findById(portfolio.getId())).thenReturn(Optional.of(portfolio));
        doNothing().when(portfolioRepository).deleteById(portfolio.getId());

        assertDoesNotThrow(()->portfolioService.deleteById(1L));

        verify(portfolioRepository).findById(portfolio.getId());
        verify(portfolioRepository).deleteById(portfolio.getId());
    }
}