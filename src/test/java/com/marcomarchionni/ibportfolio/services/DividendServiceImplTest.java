package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DividendServiceImplTest {

    @Mock
    DividendRepository dividendRepository;

    @Mock
    StrategyRepository strategyRepository;

    DividendService dividendService;

    List<Dividend> dividends;
    Dividend dividend;
    Strategy strategy;

    @BeforeEach
    void setup() {
        dividends = getSampleDividends();
        dividend = getSampleClosedDividend();
        strategy = getSampleStrategy();
        dividendService = new DividendServiceImpl(dividendRepository, strategyRepository);
    }

    @Test
    void findWithParameters() {

        when(dividendRepository.findWithParameters(any(), any(), any(), any(), any(), any())).thenReturn(dividends);

        List<Dividend> foundDividends = dividendService.findWithParameters(
                        null, null, null, null, true, "AAPL");

        assertEquals(dividends.size(), foundDividends.size() );
    }

    @Test
    void updateStrategyId() {
        Dividend commandDividend = Dividend.builder().id(dividend.getId()).strategyId(strategy.getId()).build();
        Dividend expectedDividend = getSampleClosedDividend();
        expectedDividend.setStrategyId(commandDividend.getStrategyId());

        lenient().when(dividendRepository.findById(commandDividend.getId())).thenReturn(Optional.of(dividend));
        lenient().when(strategyRepository.findById(commandDividend.getStrategyId())).thenReturn(Optional.of(strategy));
        lenient().when(dividendRepository.save(expectedDividend)).thenReturn(expectedDividend);

        Dividend actualDividend = dividendService.updateStrategyId(commandDividend);

        assertNotNull(actualDividend);
        assertEquals(commandDividend.getId(), actualDividend.getId());
        assertEquals(commandDividend.getStrategyId(), actualDividend.getStrategyId());
    }
}