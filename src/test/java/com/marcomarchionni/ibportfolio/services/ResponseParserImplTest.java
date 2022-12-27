package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.update.parsing.ResponseParser;
import com.marcomarchionni.ibportfolio.update.parsing.ResponseParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ResponseParserImplTest {

    FlexQueryResponseDto dto;

    ResponseParser responseParser;

    @BeforeEach
    void setUp() throws JAXBException {
        ClassLoader classLoader = getClass().getClassLoader();
        File xmlFile = new File(Objects.requireNonNull(classLoader.getResource("flex/LastMonth.xml")).getFile());

        Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(FlexQueryResponseDto.class).createUnmarshaller();
        dto = (FlexQueryResponseDto) jaxbUnmarshaller.unmarshal(xmlFile);
        responseParser = new ResponseParserImpl();
    }

    @Test
    void parseFlexInfo() {

        FlexInfo flexInfo = responseParser.parseFlexInfo(dto);

        assertNotNull(flexInfo);
        assertEquals("U7169936", flexInfo.getAccountId());
        assertEquals(LocalDate.of(2022, 6, 1), flexInfo.getFromDate());
        assertEquals(LocalDate.of(2022, 6, 30), flexInfo.getToDate());
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