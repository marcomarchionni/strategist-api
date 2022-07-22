package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/initIbTestDb.sql")
@Sql("/insertSampleData.sql")
class TradeRepositoryTest {

    @Autowired
    TradeRepository tradeRepository;

    @Test
    public void findWithParametersDates() {

        LocalDate min = LocalDate.of(1000, 1, 1);
        LocalDate max = LocalDate.of(9999, 12, 31);

        List<Trade> trades = tradeRepository.findWithParameters(min, max, null, null, null);

        assertEquals(7, trades.size());
    }
}