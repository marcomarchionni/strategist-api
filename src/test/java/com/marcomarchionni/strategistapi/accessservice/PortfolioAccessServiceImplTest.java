package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdEntityValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePortfolio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioAccessServiceImplTest {

    @Mock
    UserService userService;

    @Mock
    PortfolioRepository portfolioRepository;

    PortfolioAccessServiceImpl portfolioAccessService;

    @BeforeEach
    void setup() {
        var accountIdValidator = new AccountIdEntityValidatorImpl<Portfolio>();
        portfolioAccessService = new PortfolioAccessServiceImpl(portfolioRepository, userService, accountIdValidator);
        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void save() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");

        when(portfolioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var savedPortfolio = portfolioAccessService.save(portfolio);

        assertEquals(portfolio, savedPortfolio);
    }

    @Test
    void saveAccountIdNull() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId(null);

        assertThrows(InvalidUserDataException.class, () -> portfolioAccessService.save(portfolio));
    }

    @Test
    void saveException() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U2222222");

        assertThrows(InvalidUserDataException.class, () -> portfolioAccessService.save(portfolio));
    }

    @Test
    void delete() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U1111111");

        portfolioAccessService.delete(portfolio);
    }

    @Test
    void deleteException() {
        var portfolio = getSamplePortfolio("MFStockAdvisor");
        portfolio.setAccountId("U2222222");

        assertThrows(InvalidUserDataException.class, () -> portfolioAccessService.delete(portfolio));
    }
}