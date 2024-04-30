package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapper;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
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
        List<PortfolioSummary> actualPortfolios = portfolioService.findAll();

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
        PortfolioDetail actualPortfolioDto = portfolioService.findById(portfolioId);

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
        PortfolioSave portfolioSave = PortfolioSave.builder().name("NewPortfolioName").build();
        String portfolioName = portfolioSave.getName();

        // Setup mocks
        when(dataGateway.existsByName(portfolioName)).thenReturn(false);
        when(dataGateway.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute service
        PortfolioDetail createdPortfolioDto = portfolioService.create(portfolioSave);

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
        NameUpdate nameUpdate = NameUpdate.builder().id(portfolioId).name(newPortfolioName).build();

        // Setup mocks
        when(dataGateway.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));
        when(dataGateway.existsByName(newPortfolioName)).thenReturn(false);
        when(dataGateway.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute service
        PortfolioDetail actualPortfolioDto = portfolioService.updateName(nameUpdate);

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