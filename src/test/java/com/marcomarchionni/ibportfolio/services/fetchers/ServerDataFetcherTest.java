package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.services.fetchers.util.FlexServiceClientManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getPopulatedFlexQueryResponseDto;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getPopulatedFlexStatementResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerDataFetcherTest {
    @Mock
    FlexServiceClientManager clientManager;

    ServerDataFetcher serverDataFetcher;

    UpdateContextDto updateContextDto;

    FlexStatementResponseDto flexStatementResponseDto;
    FlexQueryResponseDto flexQueryResponseDto;

    @BeforeEach
    void setUp() {
        updateContextDto = UpdateContextDto.builder().sourceType(UpdateContextDto.SourceType.SERVER).queryId("queryId")
                .token("token").build();
        flexStatementResponseDto = getPopulatedFlexStatementResponseDto();
        flexQueryResponseDto = getPopulatedFlexQueryResponseDto();
        serverDataFetcher = new ServerDataFetcher(clientManager);
    }

    @Test
    void fetch() {
        // setup mock
        when(clientManager.fetchFlexStatementResponseWithRetry(any(), any()))
                .thenReturn(flexStatementResponseDto);
        when(clientManager.fetchFlexQueryResponseWithRetry(any(), any()))
                .thenReturn(flexQueryResponseDto);

        FlexQueryResponseDto responseDto = serverDataFetcher.fetch(updateContextDto);

        assertNotNull(responseDto);
        assertEquals(flexQueryResponseDto, responseDto);
    }
}