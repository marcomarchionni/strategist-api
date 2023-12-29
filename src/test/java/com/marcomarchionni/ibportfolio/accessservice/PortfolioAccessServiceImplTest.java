package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioAccessServiceImplTest {

    @Mock
    UserService userService;

    @Mock
    PortfolioRepository portfolioRepository;

    PortfolioAccessServiceImpl dataGateway;

    @BeforeEach
    void setup() {
        dataGateway = new PortfolioAccessServiceImpl(portfolioRepository, userService);
        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void save() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");

        when(portfolioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var savedPortfolio = dataGateway.save(portfolio);

        assertEquals(portfolio, savedPortfolio);
    }

    @Test
    void saveAccountIdNull() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId(null);

        when(portfolioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var savedPortfolio = dataGateway.save(portfolio);

        assertNotNull(portfolio.getAccountId());
        assertEquals("U1111111", savedPortfolio.getAccountId());
    }

    @Test
    void saveException() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U2222222");

        assertThrows(InvalidUserDataException.class, () -> dataGateway.save(portfolio));
    }

    @Test
    void delete() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U1111111");

        dataGateway.delete(portfolio);
    }

    @Test
    void deleteException() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U2222222");

        assertThrows(InvalidUserDataException.class, () -> dataGateway.delete(portfolio));
    }
}