package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeListDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.TradeMapper;
import com.marcomarchionni.ibportfolio.mappers.TradeMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class TradeServiceImplTest {

    @Mock
    TradeRepository tradeRepository;

    @Mock
    StrategyRepository strategyRepository;

    TradeService tradeService;

    private List<Trade> trades;
    private Trade trade;
    private Strategy strategy;
    private UpdateStrategyDto tradeUpdate;
    private TradeFindDto tradeCriteria;

    @BeforeEach
    void setup() {
        trades = getSampleTrades();
        trade = getSampleTrade();
        strategy = getSampleStrategy();
        tradeUpdate = UpdateStrategyDto.builder().id(trade.getId()).strategyId(strategy.getId()).build();
        tradeCriteria = getSampleTradeCriteria();
        TradeMapper tradeMapper = new TradeMapperImpl(new ModelMapper());
        tradeService = new TradeServiceImpl(tradeRepository, strategyRepository, tradeMapper);
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

        TradeListDto updatedTrade = tradeService.updateStrategyId(tradeUpdate);

        verify(tradeRepository).save(trade);
        assertEquals(tradeUpdate.getId(), updatedTrade.getId());
        assertEquals(tradeUpdate.getStrategyId(), updatedTrade.getStrategyId());
    }

    @Test
    void updateStrategyIdException() {

        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        when(strategyRepository.findById(strategy.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> tradeService.updateStrategyId(tradeUpdate));
    }

    @Test
    void findWithParametersSuccess() {

        when(tradeRepository.findByParams(any(),any(),any(),any(),any())).thenReturn(trades);
        int expectedSize = trades.size();

        List<TradeListDto> actualTrades = tradeService.findByFilter(tradeCriteria);

        assertEquals(expectedSize, actualTrades.size());
    }

    @Test
    void saveOrIgnore() {
        // setup new trades
        List<Trade> newTrades = List.of(getTTWO1Trade(), getTTWO2Trade(), getEURUSDTrade());

        // setup mock, assuming that TTWO1 and TTWO2 already exist in the database
        when(tradeRepository.existsById(getTTWO1Trade().getId())).thenReturn(true);
        when(tradeRepository.existsById(getTTWO2Trade().getId())).thenReturn(true);
        ArgumentCaptor<List<Trade>> captor = ArgumentCaptor.forClass(List.class);
        when(tradeRepository.saveAll(captor.capture())).thenAnswer(invocation -> captor.getValue());

        // execute method
        List<Trade> savedTrades = tradeService.saveOrIgnore(newTrades);

        // assertions
        assertEquals(1, savedTrades.size());
        assertEquals(getEURUSDTrade().getId(), savedTrades.get(0).getId());
    }
}