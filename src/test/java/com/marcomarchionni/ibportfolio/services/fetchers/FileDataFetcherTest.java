package com.marcomarchionni.ibportfolio.services.fetchers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FileDataFetcherTest {

    FileDataFetcher fileDataFetcher;

    @BeforeEach
    void setUp() {

        // Configure xml mapper
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Initialize file data fetcher
        fileDataFetcher = new FileDataFetcher(xmlMapper);
    }

    @Test
    void fetch() {

        // Initialize fetch context
        InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/SimpleJune2022.xml");
        assertNotNull(stream);
        FetchContext context = new FetchContext();
        context.setStream(stream);

        // Fetch data
        FlexQueryResponseDto dto = null;
        try {
            dto = fileDataFetcher.fetch(context);
        } catch (Exception e) {
            fail(e);
        }

        // Verify data
        assertNotNull(dto);
        FlexQueryResponseDto.FlexStatement flexStatement = dto.getFlexStatements().getFlexStatement();
        assertNotNull(flexStatement);
        LocalDate fromDate = flexStatement.getFromDate();
        assertEquals(LocalDate.of(2022, 6, 1), fromDate);
        int actualTradesSize = flexStatement.getTrades().getTradeList().size();
        assertEquals(10, actualTradesSize);
        int actualPositionsSize = flexStatement.getOpenPositions().getOpenPositionList().size();
        assertEquals(8, actualPositionsSize);
        int actualClosedDividendsSize = flexStatement.getChangeInDividendAccruals().getChangeInDividendAccrualList()
                .size();
        assertEquals(14, actualClosedDividendsSize);
        int actualOpenDividendsSize = flexStatement.getOpenDividendAccruals().getOpenDividendAccrualList().size();
        assertEquals(3, actualOpenDividendsSize);
    }
}