package com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclientmanagers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexResponse;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclients.FlexServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlexServiceClientManagerImpl implements FlexServiceClientManager {
    private final FlexServiceClient flexServiceClient;

    @Override
    @Retryable(backoff = @Backoff(delayExpression = "${flexservice.retry-delay}"))
    public FlexStatementResponseDto fetchFlexStatementResponseWithRetry(String queryId, String token) {
        var statementResponse = flexServiceClient.fetchFlexStatementResponse(queryId, token);

        if (hasErrors(statementResponse)) {
            throw new IbServerErrorException(statementResponse, FlexStatementResponseDto.class);
        }
        return statementResponse.getBody();
    }

    @Override
    @Retryable(backoff = @Backoff(delayExpression = "${flexservice.retry-delay}"))
    public FlexQueryResponseDto fetchFlexQueryResponseWithRetry(FlexStatementResponseDto statementResponse,
                                                                String token) {
        var queryResponse = flexServiceClient.fetchFlexQueryResponse(statementResponse, token);

        if (hasErrors(queryResponse)) {
            throw new IbServerErrorException(queryResponse, FlexQueryResponseDto.class);
        }
        return queryResponse.getBody();
    }

    private <T extends FlexResponse> boolean hasErrors(ResponseEntity<T> response) {
        return response.getStatusCode() != HttpStatus.OK ||
                response.getBody() == null ||
                !response.getBody().isPopulated();
    }
}
