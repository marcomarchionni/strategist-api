package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioListDto;
import com.marcomarchionni.ibportfolio.models.mapping.PortfolioMapper;
import com.marcomarchionni.ibportfolio.models.mapping.PortfolioMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    PortfolioMapper portfolioMapper;
    PortfolioService portfolioService;

    List<Portfolio> portfolios;
    Portfolio portfolio;

    @BeforeEach
    void setup() {
        portfolios = getSamplePortfolios();
        portfolio = getSamplePortfolio("MF StockAdvisor");
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        portfolioService = new PortfolioServiceImpl(portfolioRepository, portfolioMapper);
    }

    @Test
    void findAll() {
        when(portfolioRepository.findAll()).thenReturn(portfolios);

        List<PortfolioListDto> actualPortfolios = portfolioService.findAll();

        assertEquals(actualPortfolios.size(), portfolios.size());
    }

    @Test
    void findByIdSuccess() {
        when(portfolioRepository.findById(any())).thenReturn(Optional.of(portfolio));

        PortfolioDetailDto actualPortfolioDto = portfolioService.findById(1L);
        assertEquals(actualPortfolioDto.getId(), portfolio.getId());
        assertEquals(actualPortfolioDto.getName(), portfolio.getName());
    }

    @Test
    void findByIdException() {
        when(portfolioRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> portfolioService.findById(1L));
    }

    @Test
    void updatePortfolioNameSuccess() {
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("NewName").build();
        Portfolio expectedPortfolio = Portfolio.builder().id(1L).name("NewName").build();
        Portfolio originalPortfolio = Portfolio.builder().id(1L).name("OldName").build();

        when(portfolioRepository.findById(any())).thenReturn(Optional.of(originalPortfolio));
        when(portfolioRepository.save(expectedPortfolio)).thenReturn(expectedPortfolio);

        PortfolioDetailDto actualPortfolioDto = portfolioService.updateName(updateNameDto);

        assertNotNull(actualPortfolioDto);
        assertEquals(actualPortfolioDto.getId(), updateNameDto.getId());
        assertEquals(actualPortfolioDto.getName(), updateNameDto.getName());
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