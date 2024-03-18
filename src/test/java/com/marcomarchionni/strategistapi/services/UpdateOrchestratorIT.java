package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.*;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.mappers.DividendMapper;
import com.marcomarchionni.strategistapi.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleUser;
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
    @Autowired
    DividendMapper dividendMapper;
    UpdateContext updateContext;
    User user = getSampleUser();

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
    public void setUp() throws IOException {
        // Set up UpdateContext
        try (InputStream flexQueryStream = getClass().getResourceAsStream("/flex/Flex.xml")) {
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "Flex.xml", // filename
                    "text/xml", // content type
                    flexQueryStream // file content
            );
            updateContext = UpdateContext.builder()
                    .sourceType("FILE")
                    .file(mockMultipartFile)
                    .build();
        }
        // Set up user
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    void updateFromFileEmptyDbTest() throws IOException {

        // Execute update
        CombinedUpdateReport report = updateOrchestrator.update(updateContext);

        // Verify data

        // Get data from db
        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexStatement> flexStatements = flexStatementRepository.findAll();

        // Assert db is populated
        assertFalse(trades.isEmpty());
        assertFalse(positions.isEmpty());
        assertFalse(dividends.isEmpty());
        assertFalse(flexStatements.isEmpty());

        // Assert data belong to user account
        assertTrue(trades.stream().allMatch(t -> t.getAccountId().equals(user.getAccountId())));
        assertTrue(positions.stream().allMatch(p -> p.getAccountId().equals(user.getAccountId())));
        assertTrue(dividends.stream().allMatch(d -> d.getAccountId().equals(user.getAccountId())));
        assertTrue(flexStatements.stream().allMatch(fs -> fs.getAccountId().equals(user.getAccountId())));

        // Assert dividends' openClosed is not null
        Optional<Dividend> result = dividends.stream().filter(d -> d.getOpenClosed() == null).findFirst();
        assertTrue(result.isEmpty());

        // Assert report data reflects db data
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
        List<Dividend> existingDividends = dividendRepository.findAll();
        long countTradesWithStrategyBefore = existingTrades.stream().filter(trade -> trade.getStrategy() != null)
                .count();

        // execute update db
        CombinedUpdateReport report = updateOrchestrator.update(updateContext);

        // assess db state after update
        List<Trade> updatedTrades = tradeRepository.findAll();
        long countTradesWithStrategyAfter = updatedTrades.stream().filter(trade -> trade.getStrategy() != null).count();

        List<Position> updatedPositions = positionRepository.findAll();
        long countPositionsWithStrategyAfter = updatedPositions.stream()
                .filter(position -> position.getStrategy() != null)
                .count();
        List<Dividend> updatedDividends = dividendRepository.findAll();
        List<FlexStatement> updatedFlexStatements = flexStatementRepository.findAll();

        // assert a new flexStatement is added
        assertEquals(existingFlexStatements.size() + 1, updatedFlexStatements.size());
        Set<Long> existingFlexIds = existingFlexStatements.stream().map(FlexStatement::getId)
                .collect(Collectors.toSet());
        FlexStatement addedFlexStatement = updatedFlexStatements.stream()
                .filter(fs -> !existingFlexIds.contains(fs.getId())).toList().get(0);
        assertEquals(report.getFlexStatements().getAdded().get(0), addedFlexStatement);

        // assert trades are updated
        assertEquals(existingTrades.size() + report.getTrades().getAdded().size(), updatedTrades.size());
        assertEquals(countTradesWithStrategyBefore, countTradesWithStrategyAfter);

        // assert positions are updated
        assertEquals(report.getPositions().getAdded().size() + report.getPositions().getMerged()
                .size(), updatedPositions.size());
        assertEquals(report.getPositions().getMerged().size(), countPositionsWithStrategyAfter);

        // assert dividends are updated
        assertEquals(existingDividends.size() + report.getDividends().getAdded().size(), updatedDividends.size());
        if (!report.getDividends().getMerged().isEmpty()) {
            DividendSummary reportedMergedDividend = report.getDividends().getMerged().get(0);
            Dividend dividendBeforeMerge = existingDividends.stream()
                    .filter(dividend -> dividend.getId().equals(reportedMergedDividend.getId())).findFirst()
                    .orElse(null);
            assertNotNull(dividendBeforeMerge);
            Dividend dividendAfterMerge = updatedDividends.stream()
                    .filter(dividend -> dividend.getId().equals(reportedMergedDividend.getId())).findFirst()
                    .orElse(null);
            assertNotNull(dividendAfterMerge);

            assertEquals(Dividend.OpenClosed.OPEN, dividendBeforeMerge.getOpenClosed());
            assertEquals(reportedMergedDividend, dividendMapper.toDividendSummary(dividendAfterMerge));
        }
    }
}
