package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {"flexservice.retry-delay=1000"})
class ServerDataFetcherIT {

    @Autowired
    ServerDataFetcher serverDataFetcher;

    @Value("${flexservice.token}")
    private String token;

    @Value("${flexservice.query-id}")
    private String queryId;

    @Test
    @Disabled
    void fetch() {
        UpdateContextDto context = UpdateContextDto.builder().token(token).queryId(queryId).build();
        FlexQueryResponseDto flexQueryResponseDto = serverDataFetcher.fetch(context);

        assertNotNull(flexQueryResponseDto);
        assertNotNull(flexQueryResponseDto.getFlexStatements());
    }
}