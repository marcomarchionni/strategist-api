package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapperImpl;
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

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
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
    void findAllByUser() {
        User user = getSampleUser();
        when(portfolioRepository.findAllByAccountId(user.getAccountId())).thenReturn(portfolios);

        List<PortfolioSummaryDto> actualPortfolios = portfolioService.findAllByUser(user);

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
        long unknownId = 1L;
        when(portfolioRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> portfolioService.findById(unknownId));
    }

    @Test
    void createPortfolioSuccess() {
        User user = getSampleUser();
        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name("NewPortfolioName").build();

        when(portfolioRepository.existsByNameAndAccountId(user.getAccountId(), portfolioCreateDto.getName())).thenReturn(false);
        when(portfolioRepository.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PortfolioDetailDto createdPortfolioDto = portfolioService.create(user, portfolioCreateDto);

        assertNotNull(createdPortfolioDto);
        assertEquals(createdPortfolioDto.getName(), portfolioCreateDto.getName());
    }

    @Test
    void updatePortfolioNameSuccess() {
        User user = getSampleUser();
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("NewName").build();
        Portfolio originalPortfolio = Portfolio.builder().id(1L).name("OldName").build();

        when(portfolioRepository.findById(any())).thenReturn(Optional.of(originalPortfolio));
        when(portfolioRepository.existsByNameAndAccountId(any(), any())).thenReturn(false);
        when(portfolioRepository.save(any())).thenReturn(originalPortfolio);

        PortfolioDetailDto actualPortfolioDto = portfolioService.updateName(user, updateNameDto);

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