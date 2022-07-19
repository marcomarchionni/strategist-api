package com.marcomarchionni.ibportfolio.logging;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.services.PositionService;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class LoggingAspectTest {

    @Autowired
    TradeService tradeService;

    @Autowired
    PositionService positionService;

    Trade trade1;

    Trade trade2;

    Position position1;

    Position position2;

    List<Trade> trades;

    List<Position> positions;

    @BeforeEach
    public void setUp() {
        trade1 = Trade.builder()
                .id(1L).conId(2222L).symbol("ZM").tradeDate(LocalDate.of(2022,5, 4))
                .multiplier(1).buySell("BUY").quantity(new BigDecimal(10)).tradePrice(new BigDecimal(2500))
                .tradeMoney(new BigDecimal(2500*10)).build();
        trade2 = Trade.builder().id(2L).conId(1122233L).symbol("AAPL")
                .tradeDate(LocalDate.of(2020, 1, 1)).multiplier(1).buySell("BUY")
                .quantity(new BigDecimal(20)).tradePrice(new BigDecimal(2500))
                .tradeMoney(new BigDecimal(2500*20)).build();
        trades = new ArrayList<>(Arrays.asList(trade1, trade2));

        position1 = Position.builder().id(1L).conId(333L).symbol("ZM").quantity(new BigDecimal(43)).costBasisPrice(new BigDecimal(500))
                .markPrice(new BigDecimal(600)).multiplier(1).build();
        position2 = Position.builder().id(2L).conId(344L).symbol("AAPL").quantity(new BigDecimal(15)).costBasisPrice(new BigDecimal(300))
                .markPrice(new BigDecimal(500)).multiplier(1).build();
        positions = new ArrayList<>(Arrays.asList(position1, position2));
    }

    @Test
    public void afterReturningSaveAllTest() {

        positionService.saveAll(positions);
        tradeService.saveAll(trades);

    }

    @Test
    public void afterReturningUpdateStrategyId() {
        Trade tradeToUpdate = Trade.builder().id(1180785204L).strategyId(2L).build();
        Trade updatedTrade = tradeService.updateStrategyId(tradeToUpdate);
    }

}