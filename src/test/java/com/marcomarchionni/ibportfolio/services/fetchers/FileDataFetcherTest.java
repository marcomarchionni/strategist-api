package com.marcomarchionni.ibportfolio.services.fetchers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.ibportfolio.config.XMLConverterConfig;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidXMLFileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {XMLConverterConfig.class, FileDataFetcher.class})
class FileDataFetcherTest {

    @Autowired
    XmlMapper xmlMapper;

    @Autowired
    FileDataFetcher fileDataFetcher;

    @Test
    void fetchFileInvalid() throws IOException {

        // Initialize fetch context
        InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/Malformed.xml");
        MockMultipartFile file = new MockMultipartFile(
                "file", // the name of the parameter
                "Malformed.xml", // filename
                "text/xml", // content type
                stream // file content
        );
        assertNotNull(stream);
        UpdateContextDto context = UpdateContextDto.builder().build();
        context.setFile(file);

        // Fetch data
        try {
            fileDataFetcher.fetch(context);
        } catch (Exception e) {
            assertInstanceOf(InvalidXMLFileException.class, e);
            assertInstanceOf(JsonParseException.class, e.getCause());
        }
    }

    @Test
    void fetch() throws IOException {

        // Initialize fetch context
        InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/Flex.xml");
        assertNotNull(stream);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", // the name of the parameter
                "Flex.xml", // filename
                "text/xml", // content type
                stream // file content
        );
        UpdateContextDto context = UpdateContextDto.builder().build();
        context.setFile(mockMultipartFile);

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