package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.services.fetchers.flexserviceclientmanagers.FlexServiceClientManager;
import com.marcomarchionni.strategistapi.services.fetchers.flexserviceclients.FlexServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServerDataFetcherClientMockIT {

    final ResponseEntity<FlexStatementResponseDto> validFlexStatementResponse =
            ResponseEntity.ok(getPopulatedFlexStatementResponseDto());
    final ResponseEntity<FlexQueryResponseDto> validFlexQueryResponse =
            ResponseEntity.ok(getPopulatedFlexQueryResponseDto());
    final UpdateContext updateContext = UpdateContext.builder().sourceType(UpdateContext.SourceType.SERVER)
            .queryId("queryId")
            .token("token").build();
    @Autowired
    ServerDataFetcher serverDataFetcher;
    @Autowired
    FlexServiceClientManager flexServiceClientManager;
    @MockBean
    FlexServiceClient flexServiceClient;

    @Test
    void fetchSuccessFirstTry() {
        when(flexServiceClient.fetchFlexStatementResponse(any(), any()))
                .thenReturn(validFlexStatementResponse);
        when(flexServiceClient.fetchFlexQueryResponse(any(), any()))
                .thenReturn(validFlexQueryResponse);

        FlexQueryResponseDto responseDto = serverDataFetcher.fetch(updateContext);

        assertNotNull(responseDto);
        assertEquals(validFlexQueryResponse.getBody(), responseDto);
    }

    @Test
    void fetchSuccessSecondTryFlexStatementResponse() {
        when(flexServiceClient.fetchFlexStatementResponse(any(), any()))
                .thenReturn(ResponseEntity.internalServerError().build())
                .thenReturn(validFlexStatementResponse);
        when(flexServiceClient.fetchFlexQueryResponse(any(), any()))
                .thenReturn(validFlexQueryResponse);

        FlexQueryResponseDto responseDto = serverDataFetcher.fetch(updateContext);

        assertNotNull(responseDto);
        assertEquals(validFlexQueryResponse.getBody(), responseDto);
        verify(flexServiceClient, times(2)).fetchFlexStatementResponse(any(), any());
    }

    @Test
    void fetchSuccessThirdTryFlexQueryResponse() {
        when(flexServiceClient.fetchFlexStatementResponse(any(), any()))
                .thenReturn(validFlexStatementResponse);
        when(flexServiceClient.fetchFlexQueryResponse(any(), any()))
                .thenReturn(ResponseEntity.internalServerError().build())
                .thenReturn(ResponseEntity.ok(getEmptyFlexQueryResponseDto()))
                .thenReturn(validFlexQueryResponse);

        FlexQueryResponseDto responseDto = serverDataFetcher.fetch(updateContext);

        assertNotNull(responseDto);
        assertEquals(validFlexQueryResponse.getBody(), responseDto);
        verify(flexServiceClient, times(3)).fetchFlexQueryResponse(any(), any());
    }
}