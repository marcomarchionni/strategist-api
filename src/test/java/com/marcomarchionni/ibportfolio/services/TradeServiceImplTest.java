package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToSaveEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    Trade trade1;

    Trade trade2;

    @BeforeEach
    public void setUp() {
        trade1 = Trade.builder()
                .id(1L).conId(2222L).symbol("ZM").tradeDate(LocalDate.of(2022,5, 4))
                .multiplier(1).buySell("BUY").quantity(new BigDecimal(10)).tradePrice(new BigDecimal(2500))
                .tradeMoney(new BigDecimal(2500*10)).build();
        trade2 = Trade.builder().id(2L).conId(1122233L).symbol("AAPL")
                .tradeDate(LocalDate.of(2020, 1, 1)).multiplier(1).buySell("BUY")
                .quantity(new BigDecimal(20)).tradePrice(new BigDecimal(2500))
                .tradeMoney(new BigDecimal(2500*20)).build();
    }

    @Test
    void saveAllTest() {
        List<Trade> trades = new ArrayList<>();
        trades.add(trade1);
        trades.add(trade2);

        assertDoesNotThrow(() -> tradeService.saveAll(trades));

        Optional<Trade> optSavedTrade1 = tradeRepository.findById(1L);
        Optional<Trade> optSavedTrade2 = tradeRepository.findById(2L);
        assertTrue(optSavedTrade1.isPresent());
        assertTrue(optSavedTrade2.isPresent());
    }

    @Test
    void saveAllTestException() {
        List<Trade> trades = new ArrayList<>();
        trades.add(trade1);
        trades.add(trade2);

        assertThrows(UnableToSaveEntityException.class, () -> tradeService.saveAll(trades));
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