package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.accessservice.TradeAccessService;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.TradeFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummaryDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.TradeMapper;
import com.marcomarchionni.strategistapi.mappers.TradeMapperImpl;
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
class TradeServiceImplTest {
    @Mock
    TradeAccessService tradeAccessService;
    @Mock
    StrategyAccessService strategyAccessService;
    TradeService tradeService;
    List<Trade> trades;
    Trade trade;
    Strategy strategy;
    UpdateStrategyDto tradeUpdate;
    TradeFindDto tradeCriteria;
    User user;

    @BeforeEach
    void setup() {
        user = getSampleUser();
        trades = getSampleTrades();
        trade = getSampleTrade();
        strategy = getSampleStrategy();
        tradeUpdate = UpdateStrategyDto.builder().id(trade.getId()).strategyId(strategy.getId()).build();
        tradeCriteria = getSampleTradeCriteria();
        TradeMapper tradeMapper = new TradeMapperImpl(new ModelMapper());
        tradeService = new TradeServiceImpl(tradeAccessService, strategyAccessService, tradeMapper);
    }

    @Test
    void saveAllSuccess() {
        assertDoesNotThrow(() -> tradeService.saveAll(trades));
    }

    @Test
    void saveAllException() {
        doThrow(new RuntimeException()).when(tradeAccessService).saveAll(any());

        assertThrows(UnableToSaveEntitiesException.class, () -> tradeService.saveAll(trades));
    }

    @Test
    void updateStrategyIdSuccess() {

        when(tradeAccessService.findById(trade.getId())).thenReturn(Optional.of(trade));
        when(strategyAccessService.findById(strategy.getId())).thenReturn(Optional.of(strategy));
        when(tradeAccessService.save(trade)).thenReturn(trade);

        TradeSummaryDto updatedTrade = tradeService.updateStrategyId(tradeUpdate);

        verify(tradeAccessService).save(trade);
        assertEquals(tradeUpdate.getId(), updatedTrade.getId());
        assertEquals(tradeUpdate.getStrategyId(), updatedTrade.getStrategyId());
    }

    @Test
    void updateStrategyIdException() {

        when(tradeAccessService.findById(trade.getId())).thenReturn(Optional.of(trade));
        when(strategyAccessService.findById(strategy.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tradeService.updateStrategyId(tradeUpdate));
    }

    @Test
    void findWithParametersSuccess() {

        when(tradeAccessService.findByParams(any(), any(), any(), any(), any())).thenReturn(trades);
        int expectedSize = trades.size();

        List<TradeSummaryDto> actualTrades = tradeService.findByFilter(tradeCriteria);

        assertEquals(expectedSize, actualTrades.size());
    }

    @Test
    void updateTradesSuccess() {
        // setup new trades
        List<Trade> newTrades = List.of(getTTWO1Trade(), getTTWO2Trade(), getEURUSDTrade());

        // setup mock, assuming that TTWO1 and TTWO2 already exist in the database
        when(tradeAccessService.existsByIbOrderId(getTTWO1Trade().getIbOrderId())).thenReturn(true);
        when(tradeAccessService.existsByIbOrderId(getTTWO2Trade().getIbOrderId())).thenReturn(true);
        when(tradeAccessService.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        UpdateReport<Trade> result = tradeService.updateTrades(newTrades);

        // assertions
        assertEquals(1, result.getAdded().size());
        assertEquals(2, result.getSkipped().size());
        assertEquals("EUR.USD", result.getAdded().get(0).getSymbol());
    }

    @Test
    void updateTradesEmptyList() {
        UpdateReport<Trade> result = tradeService.updateTrades(List.of());
        assertEquals(0, result.getAdded().size());
        assertEquals(0, result.getSkipped().size());
        assertEquals(0, result.getMerged().size());
    }
}