package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileUpdaterIT {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    DividendRepository dividendRepository;
    @Autowired
    FlexStatementRepository flexStatementRepository;

    @Value("/flex/SimpleJune2022.xml")
    File flexQuery;

    @Autowired
    FileUpdater fileUpdater;

    @AfterEach
    public void cleanDb() {
        positionRepository.deleteAll();
        tradeRepository.deleteAll();
        dividendRepository.deleteAll();
        flexStatementRepository.deleteAll();
    }

    @Test
    void updateFromFileEmptyDbTest() throws IOException {

        fileUpdater.update(flexQuery);

        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexStatement> flexStatements = flexStatementRepository.findAll();

        assertFalse(trades.isEmpty());
        assertFalse(positions.isEmpty());
        assertFalse(dividends.isEmpty());
        assertFalse(flexStatements.isEmpty());
        Optional<Dividend> result = dividends.stream().filter(d -> d.getOpenClosed() == null).findFirst();
        assertTrue(result.isEmpty());
    }

    @Test
    @Sql("classpath:dbScripts/insertSampleData.sql")
    void updateFromFilePopulatedDbTest() throws IOException {

        List<FlexStatement> existingFlexStatements = flexStatementRepository.findAll();
        List<Trade> existingTrades = tradeRepository.findAll();
        long countTradesWithStrategyBefore = existingTrades.stream().filter(trade -> trade.getStrategy() != null).count();

        fileUpdater.update(flexQuery);

        List<Trade> updatedTrades = tradeRepository.findAll();
        long countTradesWithStrategyAfter = updatedTrades.stream().filter(trade -> trade.getStrategy() != null).count();

        List<Position> updatedPositions = positionRepository.findAll();
        long countPositionsWithStrategy = updatedPositions.stream().filter(position -> position.getStrategy() != null).count();
        List<Dividend> updatedDividends = dividendRepository.findAll();
        List<FlexStatement> updatedFlexStatements = flexStatementRepository.findAll();

        // assert flexStatement is updated
        assertEquals( existingFlexStatements.size() + 1, updatedFlexStatements.size());

        // assert trades are updated
        assertTrue(updatedTrades.size() >= existingTrades.size());
        assertEquals(countTradesWithStrategyBefore, countTradesWithStrategyAfter);

        // assert positions are updated
        assertEquals(3, updatedPositions.size());
        assertEquals(1, countPositionsWithStrategy);

        // assert dividends are updated
        assertEquals(6, updatedDividends.size());
        Dividend EBAYDividend = updatedDividends.stream().filter(dividend -> dividend.getSymbol().equals("EBAY")).findFirst().orElse(null);
        assertNotNull(EBAYDividend);
        assertEquals("CLOSED", EBAYDividend.getOpenClosed());
    }
}
