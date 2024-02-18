package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.services.fetchers.flexserviceclientmanagers.FlexServiceClientManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.marcomarchionni.strategistapi.util.TestUtils.getPopulatedFlexQueryResponseDto;
import static com.marcomarchionni.strategistapi.util.TestUtils.getPopulatedFlexStatementResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerDataFetcherTest {
    @Mock
    FlexServiceClientManager clientManager;

    ServerDataFetcher serverDataFetcher;

    UpdateContextReq updateContextReq;

    FlexStatementResponseDto flexStatementResponseDto;
    FlexQueryResponseDto flexQueryResponseDto;

    @BeforeEach
    void setUp() {
        updateContextReq = UpdateContextReq.builder().sourceType(UpdateContextReq.SourceType.SERVER).queryId("queryId")
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

        FlexQueryResponseDto responseDto = serverDataFetcher.fetch(updateContextReq);

        assertNotNull(responseDto);
        assertEquals(flexQueryResponseDto, responseDto);
    }
}