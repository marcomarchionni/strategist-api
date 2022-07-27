package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.TradeCriteriaDto;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @Mock
    TradeRepository tradeRepository;

    @Mock
    StrategyRepository strategyRepository;

    @InjectMocks
    TradeServiceImpl tradeService;

    private List<Trade> trades;
    private Trade trade;
    private Strategy strategy;
    private Trade requestTrade;
    private TradeCriteriaDto tradeCriteria;

    @BeforeEach
    void setup() {
        trades = getSampleTrades();
        trade = getSampleTrade();
        strategy = getSampleStrategy();
        requestTrade = Trade.builder().id(trade.getId()).strategyId(strategy.getId()).build();
        tradeCriteria = getSampleTradeCriteria();
    }

    @Test
    void saveAllSuccess() {
        assertDoesNotThrow(() -> tradeService.saveAll(trades));
    }

    @Test
    void saveAllException() {
        doThrow(new RuntimeException()).when(tradeRepository).saveAll(any());

        assertThrows(UnableToSaveEntitiesException.class, () -> tradeService.saveAll(trades));
    }

    @Test
    void updateStrategyIdSuccess() {

        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        when(strategyRepository.findById(strategy.getId())).thenReturn(Optional.of(strategy));
        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade updatedTrade = tradeService.updateStrategyId(requestTrade);

        verify(tradeRepository).save(trade);
        assertEquals(trade, updatedTrade);
    }

    @Test
    void updateStrategyIdException() {

        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        when(strategyRepository.findById(strategy.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> tradeService.updateStrategyId(requestTrade));
    }

    @Test
    void findWithParametersSuccess() {

        when(tradeRepository.findWithParameters(any(),any(),any(),any(),any())).thenReturn(trades);
        int expectedSize = trades.size();

        List<Trade> actualTrades = tradeService.findWithCriteria(tradeCriteria);

        assertEquals(expectedSize, actualTrades.size());
    }
}