package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import com.marcomarchionni.ibportfolio.services.validators.AccountIdEntityValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleDividends;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DividendAccessServiceImplTest {

    @Mock
    DividendRepository dividendRepository;

    @Mock
    UserService userService;

    DividendAccessService dividendAccessService;

    List<Dividend> dividends;
    Dividend dividend;

    @BeforeEach
    void setUp() {
        dividends = getSampleDividends();
        dividend = dividends.get(0);
        var accountIdValidator = new AccountIdEntityValidatorImpl<Dividend>();
        dividendAccessService = new DividendAccessServiceImpl(dividendRepository, userService, accountIdValidator);

        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void findByParams() {
        when(dividendRepository.findByParams(eq("U1111111"), any(), any(), any(), any(), any(), any())).thenReturn(dividends);
        List<Dividend> foundDividends = dividendAccessService.findByParams(null, null, null, null, null, "AAPL");

        assertEquals(dividends, foundDividends);
    }

    @Test
    void findById() {
        when(dividendRepository.findByIdAndAccountId(eq(1L), eq("U1111111"))).thenReturn(java.util.Optional.ofNullable(dividend));
        var foundDividend = dividendAccessService.findById(1L);

        assertEquals(dividend, foundDividend.get());
    }

    @Test
    void findBySymbol() {
        when(dividendRepository.findByAccountIdAndSymbol(eq("U1111111"), eq("AAPL"))).thenReturn(dividends);
        var foundDividends = dividendAccessService.findBySymbol("AAPL");

        assertEquals(dividends, foundDividends);
    }

    @Test
    void existsByActionId() {
        when(dividendRepository.existsByAccountIdAndActionId(eq("U1111111"), eq(1L))).thenReturn(true);
        var exists = dividendAccessService.existsByActionId(1L);

        assertTrue(exists);
    }

    @Test
    void save() {
        when(dividendRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var savedDividend = dividendAccessService.save(dividend);

        assertEquals(dividend, savedDividend);
    }

    @Test
    void saveAll() {
        when(dividendRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var savedDividends = dividendAccessService.saveAll(dividends);

        assertEquals(dividends, savedDividends);
    }

    @Test
    void saveException() {
        dividend.setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> dividendAccessService.save(dividend));
    }
}