package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.ClosedDividend;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DividendServiceImplIT {

    @Autowired
    DividendRepository dividendRepository;

    @Autowired
    DividendServiceImpl dividendService;

    Dividend closedDividend;

    @BeforeEach
    void setup() {
        closedDividend = new ClosedDividend();
        closedDividend.setId(26754720220603L);
        closedDividend.setSymbol("CGNX");
        closedDividend.setConId(267547L);
        closedDividend.setExDate(LocalDate.of(2022, 5, 19));
        closedDividend.setPayDate(LocalDate.of(2022, 6, 3));
        closedDividend.setQuantity(BigDecimal.valueOf(44));
        closedDividend.setTax(BigDecimal.valueOf(0.43));
        closedDividend.setGrossRate(BigDecimal.valueOf(0.065));
        closedDividend.setGrossAmount(BigDecimal.valueOf(2.86));
        closedDividend.setNetAmount(BigDecimal.valueOf(2.43));
    }

    @Test
    void saveClosedDividendOpenClosedEqualsClosed() {
        List<Dividend> dividends = new ArrayList<>();
        dividends.add(closedDividend);

        dividendService.saveDividends(dividends);

        Optional<Dividend> optDbDividend = dividendRepository.findById(26754720220603L);
        assertTrue(optDbDividend.isPresent());
        Dividend dbDividend = optDbDividend.get();
        assertEquals("CLOSED", dbDividend.getOpenClosed());
    }
}
