package com.marcomarchionni.ibportfolio.services.fetchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.config.WebMvcConfig;
import com.marcomarchionni.ibportfolio.config.XMLConverterConfig;
import com.marcomarchionni.ibportfolio.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SampleDataFetcherTest {

    SampleDataFetcher sampleDataFetcher;

    @Mock
    UserService userService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Configure ObjectMapper
        WebMvcConfig webMvcConfig = new WebMvcConfig();
        objectMapper = webMvcConfig.objectMapper();

        // Configure class to be tested
        XMLConverterConfig xmlConverterConfig = new XMLConverterConfig();
        var xmlMapper = xmlConverterConfig.XmlMapper();
        var resourceLoader = new DefaultResourceLoader();
        sampleDataFetcher = new SampleDataFetcher(xmlMapper, userService, resourceLoader);

        // Configure mock
        when(userService.getUserAccountId()).thenReturn("U0000000");
    }

    @Test
    void fetch() throws IOException {

        var dto = sampleDataFetcher.fetch(null);

        assertNotNull(dto);
        assertEquals("U0000000", dto.getFlexStatements().getFlexStatement().getAccountId());

        String dtoString = objectMapper.writeValueAsString(dto);
        assertFalse(dtoString.contains("U1111111"));
    }
}