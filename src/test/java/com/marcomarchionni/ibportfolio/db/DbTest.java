package com.marcomarchionni.ibportfolio.db;


import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
@DataJpaTest
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
        List<Portfolio> portfolios = portfolioRepository.findAllByAccountId("U1111111");
        assertEquals(3, portfolios.size());
    }

    @Test
    void dbSavePortfolioTest() {
        // setup
        List<Portfolio> portfoliosBefore = portfolioRepository.findAllByAccountId("U1111111");

        Portfolio portfolio = getSamplePortfolio("New Portfolio");
        portfolio.setId(null);

        // execute
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // verify
        List<Portfolio> portfoliosAfter = portfolioRepository.findAllByAccountId("U1111111");

        assertNotNull(savedPortfolio);
        assertNotNull(savedPortfolio.getId());
        assertEquals(portfoliosBefore.size() + 1, portfoliosAfter.size());
    }

    @Test
    void dbFindStrategiesTest() {
        List<Strategy> strategies = strategyRepository.findAll();
        assertNotNull(strategies);
        assertFalse(strategies.isEmpty());
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
        Dividend FDXdividend = dividendRepository.findByAccountIdAndSymbol("U1111111", "FDX").stream().findFirst()
                .get();
        assertNotNull(FDXdividend);
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

    @Test
    void deleteMissingEntity() {
        Dividend d = getFDXDividend();
        d.setId(999999999999999L);
        assertDoesNotThrow(() -> dividendRepository.delete(d));
    }
}
