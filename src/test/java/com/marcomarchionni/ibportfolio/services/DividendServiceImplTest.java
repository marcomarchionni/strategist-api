package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    DividendServiceImpl dividendService;

     List<Dividend> dividends;
     Dividend dividend;
     Dividend expectedDividend;
     Strategy strategy;
     DividendFindDto dividendCriteria;

    @BeforeEach
    void setup() {
        dividends = getSampleDividends();
        dividend = getSampleClosedDividend();
        strategy = getSampleStrategy();
        expectedDividend = getSampleClosedDividend();
        expectedDividend.setStrategy(strategy);
        dividendCriteria = getSampleDividendCriteria();
    }


    @Test
    void findWithCriteria() {

        when(dividendRepository.findWithParameters(any(), any(), any(), any(), any(), any())).thenReturn(dividends);

        List<Dividend> foundDividends = dividendService.findByParams(
                        dividendCriteria);

        assertEquals(dividends.size(), foundDividends.size() );
    }

    @Test
    void updateStrategyId() {
        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder()
                .id(dividend.getId()).strategyId(strategy.getId()).build();

        lenient().when(dividendRepository.findById(dividendUpdate.getId())).thenReturn(Optional.of(dividend));
        lenient().when(strategyRepository.findById(dividendUpdate.getStrategyId())).thenReturn(Optional.of(strategy));
        lenient().when(dividendRepository.save(expectedDividend)).thenReturn(expectedDividend);

        Dividend actualDividend = dividendService.updateStrategyId(dividendUpdate);

        assertNotNull(actualDividend);
        assertEquals(dividendUpdate.getId(), actualDividend.getId());
        assertEquals(dividendUpdate.getStrategyId(), actualDividend.getStrategy().getId());
    }
}