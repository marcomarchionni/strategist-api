package com.marcomarchionni.ibportfolio.services.parsers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.config.XMLConverterConfig;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {XMLConverterConfig.class})
class ResponseParserImplTest {

    @Autowired
    private XmlMapper xmlMapper;

    FlexStatementMapper flexStatementMapper;
    PositionMapper positionMapper;
    TradeMapper tradeMapper;
    DividendMapper dividendMapper;
    ResponseParserImpl responseParser;
    FlexQueryResponseDto flexQueryResponseDto;

    @BeforeEach
    void setUp() throws IOException {
        // get dto
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("flex/Flex.xml")) {
            assertNotNull(is);
            flexQueryResponseDto = xmlMapper.readValue(is, FlexQueryResponseDto.class);
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
}