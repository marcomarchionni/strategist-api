package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.FlexInfo;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.update.DataFetcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.time.LocalDate;
import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResponseParserImplTest {

    private FlexQueryResponseDto dto;

    @Value("classpath:/flex/LastMonth.xml")
    private Resource resource;

    @Autowired
    DataFetcher dataFetcher;

    @Autowired
    ResponseParser responseParser;

    @Autowired
    DividendRepository dividendRepository;

    @BeforeAll
    public void setup() throws Exception {
        dto = dataFetcher.fetchFromFile(resource.getFile());
    }

    @Test
    void parseFlexInfo() {
        LocalDate dtoFromDate = LocalDate.of(2022, 6, 1);
        LocalDate dtoToDate = LocalDate.of(2022, 6, 30);
        FlexInfo flexInfo = responseParser.parseFlexInfo(dto);

        assertNotNull(flexInfo);
        assertEquals("U7169936", flexInfo.getAccountId());
        assertEquals(dtoFromDate, flexInfo.getFromDate());
        assertEquals(dtoToDate, flexInfo.getToDate());
    }

    @Test
    void parseTrades() {
        List<Trade> trades = responseParser.parseTrades(dto);
        assertNotNull(trades);
        assertTrue(trades.size() > 0);
        assertEquals(10, trades.size());
    }

    @Test
    void parsePositions() {
        Position expectedPosition = getSamplePosition();
        String expectedSymbol = expectedPosition.getSymbol();

        List<Position> positions = responseParser.parsePositions(dto);

        assertNotNull(positions);
        assertEquals(94, positions.size());
        Position actualPosition = positions.stream()
                .filter(p -> p.getSymbol().equals(expectedSymbol))
                .findFirst().orElse(null);
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    void parseClosedDividends() {
        Dividend expectedClosedDividend = getSampleClosedDividend();
        String expectedSymbol = expectedClosedDividend.getSymbol();

        List<Dividend> closedDividends = responseParser.parseClosedDividends(dto);

        assertNotNull(closedDividends);
        assertTrue(closedDividends.size() > 0);
        assertEquals(3, closedDividends.size());
        Dividend actualClosedDividend = closedDividends.stream()
                .filter(d-> d.getSymbol().equals(expectedSymbol))
                .findFirst().orElse(null);
        assertEquals(expectedClosedDividend, actualClosedDividend);
    }

    @Test
    void parseOpenDividends() {
        Dividend expectedOpenDividend = getSampleOpenDividend();
        String expectedSymbol = expectedOpenDividend.getSymbol();

        List<Dividend> openDividends = responseParser.parseOpenDividends(dto);

        assertNotNull(openDividends);
        assertTrue(openDividends.size() > 0);
        assertEquals(3, openDividends.size());
        Dividend actualOpenDividend = openDividends.stream().filter(d-> d.getSymbol().equals(expectedSymbol)).findFirst().orElse(null);
        assertEquals(expectedOpenDividend, actualOpenDividend);
    }
}