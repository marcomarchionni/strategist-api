package com.marcomarchionni.ibportfolio.services.parsing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.model.mapping.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseParserImplTest {

    FlexStatementMapper flexStatementMapper;
    PositionMapper positionMapper;
    TradeMapper tradeMapper;
    DividendMapper dividendMapper;
    ResponseParserImpl responseParser;
    FlexQueryResponseDto flexQueryResponseDto;

    @BeforeEach
    void setUp() throws IOException {
        File flexQueryXml = new File(Objects.requireNonNull(
                getClass().getClassLoader().getResource("flex/SimpleJune2022.xml")
        ).getFile());
        flexQueryResponseDto = getXmlMapper().readValue(flexQueryXml, FlexQueryResponseDto.class);
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