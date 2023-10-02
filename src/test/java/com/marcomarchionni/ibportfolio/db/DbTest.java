package com.marcomarchionni.ibportfolio.db;


import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
@SpringBootTest
public class DbTest {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DividendRepository dividendRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    FlexStatementRepository flexStatementRepository;

    @Test
    void dbTest00() {

    }

    @Test
    void dbFindPortfoliosTest() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertEquals(3, portfolios.size());
    }

    @Test
    void dbSavePortfolioTest() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        int initialSize = portfolios.size();

        Portfolio portfolio = Portfolio.builder().name("BrandNewPortfolio").build();
        portfolioRepository.save(portfolio);

        portfolios = portfolioRepository.findAll();
        assertEquals(initialSize + 1, portfolios.size());
        assertEquals(4, portfolios.size());
    }

    @Test
    void dbFindStrategiesTest() {
        List<Strategy> strategies = strategyRepository.findAll();
        assertNotNull(strategies);
        assertFalse(strategies.isEmpty());
    }

    @Test
    void dbDeleteOpenDividendsTest() {
        dividendRepository.deleteOpenDividends();
        List<Dividend> closedDividends = dividendRepository.findAll();

        assertEquals(2, closedDividends.size());
    }

    @Test
    void findLastReportedDateTest() {
        LocalDate expectedDate = LocalDate.of(2022, 7, 8);

        LocalDate lastReportedDate = flexStatementRepository.findLastReportedDate();

        assertEquals(expectedDate, lastReportedDate);
    }
}
