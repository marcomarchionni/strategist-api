package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToProcessQueryException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrades;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeServiceImplTest {

    @MockBean
    StrategyRepository strategyRepository;

    @MockBean
    TradeRepository tradeRepository;

    @Autowired
    TradeService tradeService;

    @Test
    void saveAllTest() {
        List<Trade> trades = getSampleTrades();

        assertDoesNotThrow(() -> tradeService.saveAll(trades));
    }

    @Test
    void saveAllTestException() {
        List<Trade> trades = getSampleTrades();

        doThrow(new RuntimeException()).when(tradeRepository).saveAll(any());
        assertThrows(UnableToSaveEntitiesException.class, () -> tradeService.saveAll(trades));
    }

    @Test
    void updateStrategyIdTest() {
        Trade trade = TestUtils.getSampleTrade();
        Strategy strategy = getSampleStrategy();
        Trade requestTrade = Trade.builder().id(trade.getId()).strategyId(strategy.getId()).build();

        when(tradeRepository.findById(any())).thenReturn(Optional.of(trade));
        when(strategyRepository.findById(any())).thenReturn(Optional.of(strategy));
        when(tradeRepository.save(any())).thenReturn(trade);

        Trade updatedTrade = tradeService.updateStrategyId(requestTrade);

        verify(tradeRepository).save(any());
        assertEquals(trade, updatedTrade);
    }

    @Test
    void updateStrategyIdExceptionTest() {

        Trade trade = TestUtils.getSampleTrade();
        Strategy strategy = getSampleStrategy();
        Trade requestTrade = Trade.builder().id(trade.getId()).strategyId(strategy.getId()).build();

        when(tradeRepository.findById(any())).thenReturn(Optional.of(trade));
        when(strategyRepository.findById(any())).thenReturn(Optional.empty());
        when(tradeRepository.save(any())).thenReturn(trade);

        assertThrows(EntityNotFoundException.class, ()-> tradeService.updateStrategyId(requestTrade));
    }

    @Test
    void findWithParametersTest() {

        List<Trade> expectedTrades = getSampleTrades();
        when(tradeRepository.findWithParameters(any(),any(),any(),any(),any())).thenReturn(expectedTrades);

        LocalDate startDate = LocalDate.of(2022, 6, 5);
        LocalDate endDate = LocalDate.of(2022, 6, 25);

        List<Trade> trades = tradeService.findWithParameters(startDate, endDate, false, "AAPL", "STK");
        assertEquals(expectedTrades.size(), trades.size());
    }

    @Test
    void findWithParametersException(){
        when(tradeRepository.findWithParameters(any(),any(),any(),any(),any())).thenThrow(new RuntimeException());

        assertThrows(UnableToProcessQueryException.class, ()-> tradeService.findWithParameters(null, null, null, null, null));
    }
}