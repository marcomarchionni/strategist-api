package com.marcomarchionni.strategistapi.services.fetchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.strategistapi.config.WebMvcConfig;
import com.marcomarchionni.strategistapi.config.XMLConverterConfig;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.SampleDataFileNotAvailableException;
import com.marcomarchionni.strategistapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SampleDataFetcherTest {

    SampleDataFetcher sampleDataFetcher;

    @Mock
    UserService userService;

    XmlMapper xmlMapper;

    ResourceLoader resourceLoader;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Configure ObjectMapper
        WebMvcConfig webMvcConfig = new WebMvcConfig();
        objectMapper = webMvcConfig.objectMapper();

        // Configure dependencies
        XMLConverterConfig xmlConverterConfig = new XMLConverterConfig();
        xmlMapper = xmlConverterConfig.XmlMapper();
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    void fetch() throws IOException {

        // Path to the sample data file
        String path = "classpath:flex/Flex.xml";

        // build the fetcher
        sampleDataFetcher = new SampleDataFetcher(path, xmlMapper, userService, resourceLoader);

        // Mock the user service
        when(userService.getUserAccountId()).thenReturn("U0000000");

        // Execute the fetcher
        var dto = sampleDataFetcher.fetch(null);

        // Assert the result
        assertNotNull(dto);
        assertEquals("U0000000", dto.getFlexStatements().getFlexStatement().getAccountId());
        String dtoString = objectMapper.writeValueAsString(dto);
        assertFalse(dtoString.contains("U1111111"));
    }

    @Test
    void fetchException() {

        String path = "classpath:wrongPath.xml";

        sampleDataFetcher = new SampleDataFetcher(path, xmlMapper, userService, resourceLoader);

        assertThrows(SampleDataFileNotAvailableException.class, () -> sampleDataFetcher.fetch(null));
    }
}