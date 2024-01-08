package com.marcomarchionni.strategistapi.services.parsers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.config.XMLConverterConfig;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateDto;
import com.marcomarchionni.strategistapi.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

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
    void parseAllData() {
        UpdateDto updateDto = responseParser.parseAllData(flexQueryResponseDto);

        assertNotNull(updateDto);
        assertEquals("U1111111", updateDto.getFlexStatement().getAccountId());
        assertEquals(8, updateDto.getTrades().size());
        assertEquals(3, updateDto.getPositions().size());
        assertEquals(6, updateDto.getDividends().size());
    }

    @Test
    void parseHistoricalData() {
        UpdateDto updateDto = responseParser.parseHistoricalData(flexQueryResponseDto);

        assertNotNull(updateDto);
        assertEquals("U1111111", updateDto.getFlexStatement().getAccountId());
        assertEquals(8, updateDto.getTrades().size());
        assertEquals(0, updateDto.getPositions().size());
        assertEquals(3, updateDto.getDividends().size());
    }
}