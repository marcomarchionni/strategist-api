package com.marcomarchionni.ibportfolio.services.parsers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseParserImplTest {

    FlexStatementMapper flexStatementMapper;
    PositionMapper positionMapper;
    TradeMapper tradeMapper;
    DividendMapper dividendMapper;
    ResponseParserImpl responseParser;
    FlexQueryResponseDto flexQueryResponseDto;

    @BeforeEach
    void setUp() throws IOException {
        // get dto
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("flex/SimpleJune2022.xml")) {
            if (is == null) fail("Resource not found");
            flexQueryResponseDto = getXmlMapper().readValue(is, FlexQueryResponseDto.class);
        }

        // config model mapper
        ModelMapperConfig config = new ModelMapperConfig();
        ModelMapper modelMapper = config.modelMapper();
        flexStatementMapper = new FlexStatementMapperImpl(modelMapper);
        positionMapper = new PositionMapperImpl(modelMapper);
        tradeMapper = new TradeMapperImpl(modelMapper);
        dividendMapper = new DividendMapperImpl(modelMapper);
        responseParser = new ResponseParserImpl(flexStatementMapper, tradeMapper, positionMapper, dividendMapper);
    }

    @Test
    void parseFlexStatement() {
        FlexStatement flexStatement = responseParser.getFlexStatement(flexQueryResponseDto);

        assertNotNull(flexStatement);
        assertEquals("U7169936", flexStatement.getAccountId());
    }

    @Test
    void parseTrades() {
        List<Trade> trades = responseParser.getTrades(flexQueryResponseDto);

        assertNotNull(trades);
        assertEquals(8, trades.size());
    }

    @Test
    void parsePositions() {
        List<Position> positions = responseParser.getPositions(flexQueryResponseDto);

        assertNotNull(positions);
        assertEquals(3, positions.size());
    }

    @Test
    void parseClosedDividends() {
        List<Dividend> closedDividends = responseParser.getClosedDividends(flexQueryResponseDto);

        assertNotNull(closedDividends);
        assertEquals(3, closedDividends.size());
    }

    @Test
    void parseOpenDividends() {
        List<Dividend> openDividends = responseParser.getOpenDividends(flexQueryResponseDto);

        assertNotNull(openDividends);
        assertEquals(3, openDividends.size());
    }

    private XmlMapper getXmlMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper;
    }
}