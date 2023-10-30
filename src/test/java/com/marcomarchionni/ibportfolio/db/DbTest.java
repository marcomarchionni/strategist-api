package com.marcomarchionni.ibportfolio.db;


import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public static Dividend getFDXDividend() {
        Dividend div = new Dividend();
        div.setId(510058320220624L);
        div.setSymbol("FDX");
        div.setDescription("FEDEX CORPORATION");
        div.setConId(5100583L);
        div.setExDate(LocalDate.of(2022, 6, 24));
        div.setPayDate(LocalDate.of(2022, 7, 11));
        div.setQuantity(new BigDecimal("47.05"));
        div.setTax(BigDecimal.valueOf(0.11));
        div.setGrossRate(BigDecimal.valueOf(1.15));
        div.setGrossAmount(BigDecimal.valueOf(54.05));
        div.setNetAmount(BigDecimal.valueOf(45.94));
        div.setOpenClosed(Dividend.OpenClosed.OPEN);
        return div;
    }

    @Test
    void bigDecimalTest() {
        Dividend FDXdividend = dividendRepository.findById(510058320220711L).get();
        assertEquals("FDX", FDXdividend.getSymbol());
        assertEquals(new BigDecimal("47"), FDXdividend.getQuantity());
    }

    @Test
    void bigDecimalMerge() {
        Dividend d = getFDXDividend();
        assertEquals(new BigDecimal("47.05"), d.getQuantity());
        Dividend FDXMergedDividend = dividendRepository.save(d);
        assertEquals(new BigDecimal("47.05"), FDXMergedDividend.getQuantity());

        dividendRepository.findById(510058320220624L)
                .ifPresent(dividend -> assertEquals(new BigDecimal("47.05"), dividend.getQuantity()));
    }
}
