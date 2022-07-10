package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

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

        Trade trade = new Trade();
        trade.setId(1180780161L);
        trade.setStrategyId(2L);

        tradeService.updateStrategyId(trade);

        Trade updatedTrade = tradeRepository.findById(1180780161L).get();
        assertEquals(2L, updatedTrade.getStrategyId());
    }

    @Test
    void updateStrategyIdExceptionTest() {

        Optional<Trade> optTrade = tradeRepository.findById(1180780161L);
        assertTrue(optTrade.isPresent());

        Trade trade = new Trade();
        trade.setId(1180780161L);
        trade.setStrategyId(6L);

        assertThrows(EntityNotFoundException.class, ()-> tradeService.updateStrategyId(trade));
        Trade updatedTrade = tradeRepository.findById(1180780161L).get();
        assertNull(updatedTrade.getStrategyId());
    }

}