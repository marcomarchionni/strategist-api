package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class TradeServiceImplTest {

    @Autowired
    TradeService tradeService;

    @Autowired
    TradeRepository tradeRepository;

    @Test
    void findAllTest() {

        List<Trade> trades = tradeService.findAll();

        assertTrue(trades.size() > 0);
        assertEquals(7, trades.size());
    }

    @Test
    void updateStrategyIdTest() {

        Optional<Trade> optTrade = tradeRepository.findById(1180780161L);
        assertTrue(optTrade.isPresent());
        assertNotEquals(2L, optTrade.get().getStrategyId());

        Trade trade = new Trade();
        trade.setId(1180780161L);
        trade.setStrategyId(2L);

        tradeService.updateStrategyId(trade);

        Trade updatedTrade = tradeRepository.findById(1180780161L).get();
        assertEquals(2L, updatedTrade.getStrategyId());
    }

    @Test
    void updateStrategyIdExceptionTest() {

        Optional<Trade> optTrade = tradeRepository.findById(1180785204L);
        assertTrue(optTrade.isPresent());

        Trade trade = new Trade();
        trade.setId(1180785204L);
        trade.setStrategyId(6L);

        assertThrows(EntityNotFoundException.class, ()-> tradeService.updateStrategyId(trade));
        Trade updatedTrade = tradeRepository.findById(1180785204L).get();
        assertNull(updatedTrade.getStrategyId());
    }

    @Test
    void findWithParametersDates() {

        LocalDate startDate = LocalDate.of(2022, 6, 5);
        LocalDate endDate = LocalDate.of(2022, 6, 30);

        List<Trade> trades = tradeService.findWithParameters(null, endDate, null, null, null);

        assertEquals(6, trades.size());
    }

    @Test
    void findWithParametersDatesMinMax() {

        LocalDate startDate = LocalDate.MIN;
        LocalDate endDate = LocalDate.MAX;

        List<Trade> trades = tradeService.findWithParameters(startDate, endDate, null, null, null);

        assertEquals(7, trades.size());
    }


    @ParameterizedTest
    @CsvSource({"false,,STK,FVRR","true,,STK,ZM","true,,OPT,IBKR  220617P00055000"})
    void findWithParametersAllParametrized(boolean tagged, String symbol, String assetCategory, String expectedSymbol) {

        LocalDate startDate = LocalDate.of(2022, 6, 5);
        LocalDate endDate = LocalDate.of(2022, 6, 25);

        List<Trade> trades = tradeService.findWithParameters(startDate, endDate, tagged, symbol, assetCategory);
        assertEquals(1, trades.size());
        assertEquals(expectedSymbol, trades.get(0).getSymbol());
    }
}