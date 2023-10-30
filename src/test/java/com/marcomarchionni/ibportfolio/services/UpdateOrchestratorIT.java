package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UpdateOrchestratorIT {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    DividendRepository dividendRepository;
    @Autowired
    FlexStatementRepository flexStatementRepository;
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    PortfolioRepository portfolioRepository;


    @Autowired
    UpdateOrchestrator updateOrchestrator;

    InputStream flexQueryStream;

    @AfterEach
    public void cleanDb() {
        positionRepository.deleteAll();
        tradeRepository.deleteAll();
        dividendRepository.deleteAll();
        flexStatementRepository.deleteAll();
        strategyRepository.deleteAll();
        portfolioRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        flexQueryStream = getClass().getResourceAsStream("/flex/SimpleJune2022.xml");
    }

    @Test
    void updateFromFileEmptyDbTest() throws IOException {

        CombinedUpdateReport report = updateOrchestrator.updateFromFile(flexQueryStream);

        // get data from db
        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexStatement> flexStatements = flexStatementRepository.findAll();

        // assert db is populated
        assertFalse(trades.isEmpty());
        assertFalse(positions.isEmpty());
        assertFalse(dividends.isEmpty());
        assertFalse(flexStatements.isEmpty());

        // assert dividends' openClosed is not null
        Optional<Dividend> result = dividends.stream().filter(d -> d.getOpenClosed() == null).findFirst();
        assertTrue(result.isEmpty());

        // assert report data reflects db data
        assertEquals(trades.size(), report.getTrades().getAdded().size());
        assertEquals(positions.size(), report.getPositions().getAdded().size());
        assertEquals(dividends.size(), report.getDividends().getAdded().size());
        assertEquals(flexStatements.size(), report.getFlexStatements().getAdded().size());
    }

    @Test
    @Sql("classpath:dbScripts/insertSampleData.sql")
    void updateFromFilePopulatedDbTest() throws IOException {

        // assess db state before update
        List<FlexStatement> existingFlexStatements = flexStatementRepository.findAll();
        List<Trade> existingTrades = tradeRepository.findAll();
        long countTradesWithStrategyBefore = existingTrades.stream().filter(trade -> trade.getStrategy() != null)
                .count();
        List<Dividend> existingDividends = dividendRepository.findAll();

        // update db
        CombinedUpdateReport report = updateOrchestrator.updateFromFile(flexQueryStream);

        // assess db state after update
        List<Trade> updatedTrades = tradeRepository.findAll();
        long countTradesWithStrategyAfter = updatedTrades.stream().filter(trade -> trade.getStrategy() != null).count();

        List<Position> updatedPositions = positionRepository.findAll();
        long countPositionsWithStrategy = updatedPositions.stream().filter(position -> position.getStrategy() != null)
                .count();
        List<Dividend> updatedDividends = dividendRepository.findAll();
        List<FlexStatement> updatedFlexStatements = flexStatementRepository.findAll();

        // assert flexStatement is added
        assertEquals(existingFlexStatements.size() + 1, updatedFlexStatements.size());
        FlexStatement lastAddedFlex = updatedFlexStatements.stream()
                .filter(fs -> fs.getId().equals(report.getFlexStatements().getAdded().get(0).getId())).findFirst()
                .get();
        assertEquals(report.getFlexStatements().getAdded().get(0), lastAddedFlex);

        // assert trades are updated
        assertEquals(existingTrades.size() + report.getTrades().getAdded().size(), updatedTrades.size());
        assertEquals(countTradesWithStrategyBefore, countTradesWithStrategyAfter);

        // assert positions are updated
        assertEquals(report.getPositions().getAdded().size() + report.getPositions().getMerged()
                .size(), updatedPositions.size());
        assertEquals(report.getPositions().getMerged().size(), countPositionsWithStrategy);

        // assert dividends are updated
        assertEquals(existingDividends.size() + report.getDividends().getAdded().size(), updatedDividends.size());
        if (!report.getDividends().getMerged().isEmpty()) {
            Dividend reportedMergedDividend = report.getDividends().getMerged().get(0);
            Dividend dividendBeforeMerge = existingDividends.stream()
                    .filter(dividend -> dividend.getId().equals(reportedMergedDividend.getId())).findFirst()
                    .orElse(null);
            assertNotNull(dividendBeforeMerge);
            Dividend dividendAfterMerge = updatedDividends.stream()
                    .filter(dividend -> dividend.getId().equals(reportedMergedDividend.getId())).findFirst()
                    .orElse(null);
            assertNotNull(dividendAfterMerge);
            assertEquals(Dividend.OpenClosed.OPEN, dividendBeforeMerge.getOpenClosed());
            assertEquals(reportedMergedDividend, dividendAfterMerge);
        }
    }
}
