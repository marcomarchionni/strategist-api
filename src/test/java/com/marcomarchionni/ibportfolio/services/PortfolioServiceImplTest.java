package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.PortfolioAccessService;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    PortfolioAccessService dataGateway;
    @Mock
    UserService userService;
    PortfolioMapper portfolioMapper;
    PortfolioService portfolioService;
    User user;
    Portfolio userPortfolio;

    @BeforeEach
    void setup() {
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        portfolioService = new PortfolioServiceImpl(dataGateway, userService, portfolioMapper);

        user = getSampleUser();
        userPortfolio = getSamplePortfolio("MFStockAdvisor");
    }

    @Test
    void findAllByUser() {
        // Setup test data
        String accountId = user.getAccountId();
        List<Portfolio> portfolios = getSamplePortfolios();
        portfolios.forEach(portfolio -> portfolio.setAccountId(accountId));

        // Setup mocks
        when(dataGateway.findAll()).thenReturn(portfolios);

        // Execute service
        List<PortfolioSummaryDto> actualPortfolios = portfolioService.findAll();

        // Verify results
        assertEquals(actualPortfolios.size(), portfolios.size());
    }

    @Test
    void findByIdSuccess() {
        // Setup test data
        Long portfolioId = userPortfolio.getId();

        // Setup mocks
        when(dataGateway.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));

        // Execute service
        PortfolioDetailDto actualPortfolioDto = portfolioService.findById(portfolioId);

        // Verify results
        assertEquals(actualPortfolioDto.getId(), userPortfolio.getId());
        assertEquals(actualPortfolioDto.getName(), userPortfolio.getName());
    }

    @Test
    void findByIdException() {
        // Setup test data
        Long unknownPortfolioId = 1L;

        // Setup mocks
        when(dataGateway.findById(unknownPortfolioId)).thenReturn(Optional.empty());

        // Execute service and verify exception
        assertThrows(EntityNotFoundException.class, () -> portfolioService.findById(unknownPortfolioId));
    }

    @Test
    void createPortfolioSuccess() {
        // Setup test data
        PortfolioCreateDto portfolioCreateDto = PortfolioCreateDto.builder().name("NewPortfolioName").build();
        String portfolioName = portfolioCreateDto.getName();

        // Setup mocks
        when(dataGateway.existsByName(portfolioName)).thenReturn(false);
        when(dataGateway.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute service
        PortfolioDetailDto createdPortfolioDto = portfolioService.create(portfolioCreateDto);

        // Verify results
        assertNotNull(createdPortfolioDto);
        assertEquals(portfolioName, createdPortfolioDto.getName());
    }

    @Test
    void updatePortfolioNameSuccess() {
        // Setup test data
        String accountId = user.getAccountId();
        Long portfolioId = userPortfolio.getId();
        String newPortfolioName = "NewName";
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(portfolioId).name(newPortfolioName).build();

        // Setup mocks
        when(dataGateway.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));
        when(dataGateway.existsByName(newPortfolioName)).thenReturn(false);
        when(dataGateway.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute service
        PortfolioDetailDto actualPortfolioDto = portfolioService.updateName(updateNameDto);

        // Verify results
        assertNotNull(actualPortfolioDto);
        assertEquals(newPortfolioName, actualPortfolioDto.getName());
        assertEquals(portfolioId, actualPortfolioDto.getId());
        assertEquals(accountId, actualPortfolioDto.getAccountId());
    }

    @Test
    void deleteByIdSuccess() {
        // setup test data
        Long portfolioId = userPortfolio.getId();

        // setup mocks
        when(dataGateway.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));
        doNothing().when(dataGateway).delete(userPortfolio);

        // execute service
        assertDoesNotThrow(() -> portfolioService.deleteById(portfolioId));

        // verify results
        verify(dataGateway).findById(portfolioId);
        verify(dataGateway).delete(userPortfolio);
    }

    @Test
    void deleteByIdException() {
        // setup test data
        Long unknownPortfolioId = 1L;

        // setup mocks
        when(dataGateway.findById(unknownPortfolioId)).thenReturn(Optional.empty());

        // execute service and verify exception
        assertThrows(EntityNotFoundException.class, () -> portfolioService.deleteById(unknownPortfolioId));
    }
}