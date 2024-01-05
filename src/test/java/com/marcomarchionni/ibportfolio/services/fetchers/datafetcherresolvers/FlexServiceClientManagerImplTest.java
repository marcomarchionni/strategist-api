package com.marcomarchionni.ibportfolio.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclientmanagers.FlexServiceClientManager;
import com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclients.FlexServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class FlexServiceClientManagerImplTest {

    @Autowired
    FlexServiceClientManager flexServiceClientManager;

    @MockBean
    FlexServiceClient flexServiceClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void fetchFlexStatementResponseWithRetry() {
        // build valid and invalid response entities
        ResponseEntity<FlexStatementResponseDto> invalidFlexStatementResponse = ResponseEntity.internalServerError()
                .build();
        ResponseEntity<FlexStatementResponseDto> invalidFlexStatementResponse2 =
                ResponseEntity.ok(getEmptyFlexStatementResponseDto());
        ResponseEntity<FlexStatementResponseDto> validFlexStatementResponse =
                ResponseEntity.ok(getPopulatedFlexStatementResponseDto());


        when(flexServiceClient.fetchFlexStatementResponse(any(), any()))
                .thenReturn(invalidFlexStatementResponse)
                .thenReturn(invalidFlexStatementResponse2)
                .thenReturn(validFlexStatementResponse);

        // execute method
        FlexStatementResponseDto responseDto = flexServiceClientManager.fetchFlexStatementResponseWithRetry("queryId"
                , "token");

        // assert
        assertNotNull(responseDto);
        assertEquals(validFlexStatementResponse.getBody(), responseDto);
        verify(flexServiceClient, times(3)).fetchFlexStatementResponse(any(), any());
    }

    @Test
    void fetchFlexQueryResponseWithRetry() {
        ResponseEntity<FlexQueryResponseDto> invalidFlexQueryResponse = ResponseEntity.internalServerError().build();
        ResponseEntity<FlexQueryResponseDto> invalidFlexQueryResponse2 =
                ResponseEntity.ok(getEmptyFlexQueryResponseDto());
        ResponseEntity<FlexQueryResponseDto> validFlexQueryResponse =
                ResponseEntity.ok(getPopulatedFlexQueryResponseDto());

        when(flexServiceClient.fetchFlexQueryResponse(any(), any()))
                .thenReturn(invalidFlexQueryResponse)
                .thenReturn(invalidFlexQueryResponse2)
                .thenReturn(validFlexQueryResponse);

        FlexQueryResponseDto responseDto =
                flexServiceClientManager.fetchFlexQueryResponseWithRetry(getPopulatedFlexStatementResponseDto(),
                        "token");

        assertNotNull(responseDto);
        assertEquals(validFlexQueryResponse.getBody(), responseDto);
        verify(flexServiceClient, times(3)).fetchFlexQueryResponse(any(), any());
    }

    @Test
    void fetchFlexQueryResponseWithRetryFailure() {
        ResponseEntity<FlexQueryResponseDto> invalidFlexQueryResponse = ResponseEntity.internalServerError().build();

        when(flexServiceClient.fetchFlexQueryResponse(any(), any()))
                .thenReturn(invalidFlexQueryResponse);

        assertThrows(IbServerErrorException.class,
                () -> flexServiceClientManager.fetchFlexQueryResponseWithRetry(getPopulatedFlexStatementResponseDto(),
                        "token"));

        verify(flexServiceClient, times(3)).fetchFlexQueryResponse(any(), any());
    }
}