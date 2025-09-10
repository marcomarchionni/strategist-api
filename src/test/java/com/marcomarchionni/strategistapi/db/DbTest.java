package com.marcomarchionni.strategistapi.db;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePortfolio;
import static org.junit.jupiter.api.Assertions.*;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.portfolios.repo.PortfolioRepository;
import com.marcomarchionni.strategistapi.repositories.*;
import com.marcomarchionni.strategistapi.strategies.repo.StrategyRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DataJpaTest
public class DbTest {

  @Autowired TradeRepository tradeRepository;

  @Autowired PositionRepository positionRepository;

  @Autowired DividendRepository dividendRepository;

  @Autowired PortfolioRepository portfolioRepository;

  @Autowired StrategyRepository strategyRepository;

  @Autowired FlexStatementRepository flexStatementRepository;

  @BeforeEach
  void setUp() {
    // Create test portfolios
    Portfolio portfolio1 = new Portfolio();
    portfolio1.setName("Saver Portfolio");
    portfolio1.setDescription("This is a Savers Portfolio");
    portfolio1.setAccountId("U1111111");
    portfolio1.setCreatedAt(LocalDate.of(2021, 1, 1));
    portfolioRepository.save(portfolio1);

    Portfolio portfolio2 = new Portfolio();
    portfolio2.setName("Trader Portfolio");
    portfolio2.setDescription("This is a Traders Portfolio");
    portfolio2.setAccountId("U1111111");
    portfolio2.setCreatedAt(LocalDate.of(2021, 2, 1));
    portfolioRepository.save(portfolio2);

    Portfolio portfolio3 = new Portfolio();
    portfolio3.setName("Millionaire Portfolio");
    portfolio3.setDescription("This is a Millionaires Portfolio");
    portfolio3.setAccountId("U1111111");
    portfolio3.setCreatedAt(LocalDate.of(2022, 3, 1));
    portfolioRepository.save(portfolio3);

    Portfolio portfolio4 = new Portfolio();
    portfolio4.setName("Option Portfolio");
    portfolio4.setDescription("This is a Traders Portfolio");
    portfolio4.setAccountId("U1111111");
    portfolio4.setCreatedAt(LocalDate.of(2024, 5, 1));
    portfolioRepository.save(portfolio4);

    // Create test strategies
    Strategy strategy1 = new Strategy();
    strategy1.setName("ZM long");
    strategy1.setAccountId("U1111111");
    strategy1.setPortfolio(portfolio1);
    strategyRepository.save(strategy1);

    Strategy strategy2 = new Strategy();
    strategy2.setName("IBKR put");
    strategy2.setAccountId("U1111111");
    strategy2.setPortfolio(portfolio2);
    strategyRepository.save(strategy2);

    // Create test dividend
    Dividend dividend = new Dividend();
    dividend.setId(510058320220624L);
    dividend.setAccountId("U1111111");
    dividend.setSymbol("FDX");
    dividend.setDescription("FEDEX CORPORATION");
    dividend.setConId(5100583L);
    dividend.setExDate(LocalDate.of(2022, 6, 24));
    dividend.setPayDate(LocalDate.of(2022, 7, 11));
    dividend.setQuantity(new BigDecimal("47"));
    dividend.setTax(BigDecimal.valueOf(0.11));
    dividend.setGrossRate(BigDecimal.valueOf(1.15));
    dividend.setGrossAmount(BigDecimal.valueOf(54.05));
    dividend.setNetAmount(BigDecimal.valueOf(45.94));
    dividend.setOpenClosed(Dividend.OpenClosed.OPEN);
    dividend.setStrategy(strategy1);
    dividendRepository.save(dividend);
  }

  @Test
  void dbFindPortfoliosTest() {
    List<Portfolio> portfolios = portfolioRepository.findAllByAccountId("U1111111");
    assertEquals(4, portfolios.size());
  }

  @Test
  void dbSavePortfolioTest() {
    // setup
    List<Portfolio> portfoliosBefore = portfolioRepository.findAllByAccountId("U1111111");

    Portfolio portfolio = getSamplePortfolio("New Portfolio");
    portfolio.setId(null);
    portfolio.setAccountId("U1111111");
    portfolio.setCreatedAt(LocalDate.of(2024, 1, 1));

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
    div.setAccountId("U1111111");
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
    Dividend FDXdividend =
        dividendRepository.findByAccountIdAndSymbol("U1111111", "FDX").stream().findFirst().get();
    assertNotNull(FDXdividend);
    assertEquals(new BigDecimal("47"), FDXdividend.getQuantity());
  }

  @Test
  void bigDecimalMerge() {
    Dividend d = getFDXDividend();
    assertEquals(new BigDecimal("47.05"), d.getQuantity());
    Dividend FDXMergedDividend = dividendRepository.save(d);
    assertEquals(new BigDecimal("47.05"), FDXMergedDividend.getQuantity());

    dividendRepository
        .findById(510058320220624L)
        .ifPresent(dividend -> assertEquals(new BigDecimal("47.05"), dividend.getQuantity()));
  }

  @Test
  void deleteMissingEntity() {
    Dividend d = getFDXDividend();
    d.setId(999999999999999L);
    assertDoesNotThrow(() -> dividendRepository.delete(d));
  }
}
