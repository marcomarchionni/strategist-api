package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.config.XMLConverterConfig;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {XMLConverterConfig.class, ServerDataFetcher.class})
@TestPropertySource(locations = "classpath:application.properties")
@Disabled
class ServerDataFetcherTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ServerDataFetcher serverDataFetcher;

    @Test
    void fetch() {
        assertNotNull(serverDataFetcher);

        FlexQueryResponseDto flexQueryResponseDto = serverDataFetcher.fetch(new FetchContext());

        assertNotNull(flexQueryResponseDto);
        String accountId = flexQueryResponseDto.getFlexStatements().getFlexStatement().getAccountId();
        assertEquals("U7169936", accountId);

    }
}