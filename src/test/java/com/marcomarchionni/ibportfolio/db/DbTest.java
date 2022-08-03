package com.marcomarchionni.ibportfolio.db;


import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
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

    @Test
    void dBTest1() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        int initialSize = portfolios.size();

        Portfolio portfolio = Portfolio.builder().name("BrandNewPortfolio").build();
        portfolioRepository.save(portfolio);

        portfolios = portfolioRepository.findAll();
        assertEquals(initialSize + 1, portfolios.size());
        assertEquals(4, portfolios.size());
    }

    @Test
    void dBTest2() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertEquals(3, portfolios.size());
    }

    @Test
    void dbTest3() {
        List<Strategy> strategies = strategyRepository.findAll();
        assertNotNull(strategies);
        assertTrue(strategies.size() >0);
    }
}
