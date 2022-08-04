package com.marcomarchionni.ibportfolio.util;

import com.marcomarchionni.ibportfolio.model.domain.*;
import com.marcomarchionni.ibportfolio.model.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.TradeFindDto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static Trade getSampleTrade() {
        return getZMTrade();
    }

    public static List<Trade> getSampleTrades() {
        List<Trade> trades = new ArrayList<>();
        trades.add(getZMTrade());
        trades.add(getFVRRTrade());
        trades.add(getTTWO1Trade());
        trades.add(getTTWO2Trade());
        trades.add(getIBKROpt1Trade());
        trades.add(getIBKROpt2Trade());
        trades.add(getEURUSDTrade());
        return trades;
    }

    public static Position getSamplePosition() {
        return getADYENPosition();
    }

    public static List<Position> getSamplePositions() {
        return new ArrayList<>(Arrays.asList(getADYENPosition(), getADBEPosition()));
    }

    public static Dividend getSampleClosedDividend() {
        return getEBAYClosedDividend();
    }

    public static Dividend getSampleOpenDividend() {
        return getFDXOpenDividend();
    }

    public static List<Dividend> getSampleDividends() {
        List<Dividend> dividends = new ArrayList<>();
        dividends.add(getEBAYClosedDividend());
        dividends.add(getFDXOpenDividend());
        return dividends;
    }

    public static Strategy getSampleStrategy() {
        return getZMStrategy();
    }

    public static List<Strategy> getSampleStrategies() {
        List<Strategy> strategies = new ArrayList<>();
        strategies.add(getZMStrategy());
        strategies.add(getEBAYStrategy());
        return strategies;
    }

    public static Strategy getEBAYStrategy() {
        return Strategy.builder().id(2L).name("EBAY covcall").build();
    }

    public static Strategy getZMStrategy() {
        return Strategy.builder().id(1L).name("ZM long").build();
    }

    public static List<Portfolio> getSamplePortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(getSamplePortfolio("MFStockAdvisor"));
        portfolios.add(getSamplePortfolio("MFOptions"));
        return portfolios;
    }

    public static Portfolio getSamplePortfolio(String portfolioName) {
        return Portfolio.builder().name(portfolioName).build();
    }

    public static TradeFindDto getSampleTradeCriteria() {
        return TradeFindDto.builder()
                .tradeDateFrom(LocalDate.of(2022, 6, 5))
                .tradeDateTo(LocalDate.of(2022, 6, 25))
                .tagged(false)
                .symbol("AAPL")
                .assetCategory("STK")
                .build();
    }

    public static PositionFindDto getSamplePositionCriteria() {
        return PositionFindDto.builder()
                .tagged(true)
                .symbol("AAPL")
                .build();
    }

    public static DividendFindDto getSampleDividendCriteria() {
        return DividendFindDto.builder()
                .exDateFrom(LocalDate.of(2022,6,5))
                .build();
    }

    private static Dividend getFDXOpenDividend() {
        return Dividend.builder().id(Long.parseLong("510058320220624")).conId(5100583L)
                .symbol("FDX").description("FEDEX CORPORATION").exDate(LocalDate.of(2022, 6, 24))
                .payDate(LocalDate.of(2022,7,11)).quantity(new BigDecimal(47))
                .grossRate(new BigDecimal("1.15")).grossAmount(new BigDecimal("54.05"))
                .netAmount(new BigDecimal("45.94")).tax(new BigDecimal("8.11")).build();
    }

    private static Dividend getEBAYClosedDividend() {
        return Dividend.builder().id(Long.parseLong("434708620220531")).conId(4347086L)
                .symbol("EBAY").description("EBAY INC").exDate(LocalDate.of(2022, 5, 31))
                .payDate(LocalDate.of(2022,6,17)).quantity(new BigDecimal(100))
                .grossRate(new BigDecimal("0.22")).grossAmount(new BigDecimal("22"))
                .netAmount(new BigDecimal("18.7")).tax(new BigDecimal("3.3")).build();
    }

    private static Position getADYENPosition() {
        return Position.builder()
                .id(321202935L).conId(321202935L).reportDate(LocalDate.of(2022, 6, 30))
                .symbol("ADYEN").description("ADYEN NV").assetCategory("STK").quantity(new BigDecimal(1))
                .costBasisPrice(new BigDecimal("1578.8"))
                .markPrice(new BigDecimal("1388")).multiplier(1).build();
    }

    private static Position getADBEPosition() {
        return Position.builder()
                .id(265768L).conId(265768L).reportDate(LocalDate.of(2022, 7, 7))
                .symbol("ADBE").description("ADOBE INC").assetCategory("STK").quantity(new BigDecimal(10))
                .costBasisPrice(new BigDecimal("434.4900"))
                .markPrice(new BigDecimal("390.8900")).multiplier(1).build();
    }

    private static Trade getZMTrade() {
        return Trade.builder()
                .id(1180780161L)
                .tradeId(387679436L)
                .conId(361181057L)
                .strategy(getSampleStrategy())
                .tradeDate(LocalDate.of(2022, 6, 7))
                .symbol("ZM")
                .assetCategory("STK")
                .multiplier(1)
                .buySell("BUY")
                .quantity(BigDecimal.valueOf(15))
                .tradePrice(BigDecimal.valueOf(111.3300))
                .tradeMoney(BigDecimal.valueOf(1669.9500))
                .build();
    }

    private static Trade getFVRRTrade() {
        return Trade.builder()
                .id(1180785204L)
                .tradeId(387681643L)
                .conId(370695082L)
                .strategy(getSampleStrategy())
                .tradeDate(LocalDate.of(2022, 6, 7))
                .symbol("FVRR")
                .assetCategory("STK")
                .multiplier(1)
                .buySell("BUY")
                .quantity(BigDecimal.valueOf(10))
                .tradePrice(BigDecimal.valueOf(40.5450))
                .tradeMoney(BigDecimal.valueOf(405.4500))
                .build();
    }

    private static Trade getIBKROpt1Trade() {
        return Trade.builder()
                .id(1198688377L)
                .tradeId(391765250L)
                .conId(520934333L)
                .tradeDate(LocalDate.of(2022, 6, 15))
                .symbol("IBKR 220617P00055000")
                .assetCategory("OPT")
                .multiplier(100)
                .putCall("P")
                .strike(BigDecimal.valueOf(55))
                .expiry(LocalDate.parse("2022-06-17"))
                .buySell("BUY")
                .quantity(BigDecimal.valueOf(1))
                .tradePrice(BigDecimal.valueOf(0.5500))
                .tradeMoney(BigDecimal.valueOf(55.0000))
                .fifoPnlRealized(BigDecimal.valueOf(213.0981))
                .build();
    }

    private static Trade getIBKROpt2Trade() {
        return Trade.builder()
                .id(1198688378L)
                .tradeId(391765279L)
                .conId(539457903L)
                .tradeDate(LocalDate.of(2022, 6, 15))
                .symbol("IBKR 220916P00055000")
                .assetCategory("OPT")
                .multiplier(100)
                .putCall("P")
                .strike(BigDecimal.valueOf(55))
                .expiry(LocalDate.of(2022, 9, 16))
                .buySell("SELL")
                .quantity(BigDecimal.valueOf(-1))
                .tradePrice(BigDecimal.valueOf(3.7000))
                .tradeMoney(BigDecimal.valueOf(-370.0000))
                .fifoPnlRealized(BigDecimal.valueOf(0))
                .build();
    }

    private static Trade getTTWO1Trade() {
        return Trade.builder()
                .id(1222538552L)
                .conId(6478131L)
                .tradeDate(LocalDate.of(2022, 6, 28))
                .symbol("TTWO")
                .assetCategory("STK")
                .multiplier(1)
                .buySell("SELL (Ca.)")
                .quantity(BigDecimal.valueOf(0.7440))
                .tradePrice(BigDecimal.valueOf(115.2709))
                .tradeMoney(BigDecimal.valueOf(85.7615))
                .fifoPnlRealized(BigDecimal.valueOf(0))
                .build();
    }

    private static Trade getTTWO2Trade() {
        return Trade.builder()
                .id(1222538553L)
                .conId(6478131L)
                .tradeDate(LocalDate.of(2022, 6, 28))
                .symbol("TTWO")
                .assetCategory("STK")
                .multiplier(1)
                .buySell("SELL")
                .quantity(BigDecimal.valueOf(-0.7440))
                .tradePrice(BigDecimal.valueOf(122.6900))
                .tradeMoney(BigDecimal.valueOf(-91.2814))
                .fifoPnlRealized(BigDecimal.valueOf(-0.6919))
                .build();
    }

    private static Trade getEURUSDTrade() {
        return Trade.builder()
                .id(1238155321L)
                .tradeId(400172483L)
                .conId(12087792L)
                .tradeDate(LocalDate.of(2022, 7, 6))
                .symbol("EUR.USD")
                .assetCategory("CASH")
                .multiplier(1)
                .buySell("BUY")
                .quantity(BigDecimal.valueOf(1))
                .tradePrice(BigDecimal.valueOf(1.0174))
                .tradeMoney(BigDecimal.valueOf(1.0174))
                .fifoPnlRealized(BigDecimal.valueOf(-0.6919))
                .build();
    }
}
