package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.config.XMLConvertersConfig;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {XMLConvertersConfig.class, ServerDataFetcher.class})
@TestPropertySource(locations = "classpath:application.properties")
@Disabled
class ServerDataFetcherTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ServerDataFetcher serverDataFetcher;

    @Test
    void fetch() throws IOException {
        assertNotNull(serverDataFetcher);

        FlexQueryResponseDto flexQueryResponseDto = serverDataFetcher.fetch(new FetchContext());

        assertNotNull(flexQueryResponseDto);
        LocalDate fromDate = flexQueryResponseDto.getFlexStatements().getFlexStatement().getFromDate();
        assertEquals(LocalDate.of(2023, 9, 1), fromDate);

    }
}