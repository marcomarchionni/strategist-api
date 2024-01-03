package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Disabled
class ServerDataFetcherTest {

    @Autowired
    ServerDataFetcher serverDataFetcher;

    @Test
    void fetch() {
        assertNotNull(serverDataFetcher);

        FlexQueryResponseDto flexQueryResponseDto = serverDataFetcher.fetch(UpdateContextDto.builder().build());

        assertNotNull(flexQueryResponseDto);
        String accountId = flexQueryResponseDto.getFlexStatements().getFlexStatement().getAccountId();
        assertEquals("U7169936", accountId);

    }
}