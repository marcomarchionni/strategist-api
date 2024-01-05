package com.marcomarchionni.ibportfolio.services.fetchers.util;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexResponse;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlexServiceClientManagerImpl implements FlexServiceClientManager {

    private final FlexServiceClient flexServiceClient;

    @Override
    @Retryable
    public FlexStatementResponseDto fetchFlexStatementResponseWithRetry(String queryId, String token) {
        log.info("Fetching flex statement response with queryId: {} and token: {}", queryId, token);
        var statementResponse = flexServiceClient.fetchFlexStatementResponse(queryId, token);

        if (hasErrors(statementResponse)) {
            log.warn("Flex statement response has errors: {}", statementResponse);
            throw new IbServerErrorException(statementResponse, FlexStatementResponseDto.class);
        }
        log.info("Flex statement response fetched successfully");
        return statementResponse.getBody();
    }

    @Override
    @Retryable
    public FlexQueryResponseDto fetchFlexQueryResponseWithRetry(FlexStatementResponseDto statementResponse,
                                                                String token) {
        log.info("Fetching flex query response with url: {} and code: {}", statementResponse.getUrl(),
                statementResponse.getReferenceCode());
        var queryResponse = flexServiceClient.fetchFlexQueryResponse(statementResponse, token);

        if (hasErrors(queryResponse)) {
            log.warn("Flex query response has errors: {}", queryResponse);
            throw new IbServerErrorException("Flex query response has errors");
        }
        log.info("Flex query response fetched successfully");
        return queryResponse.getBody();
    }

    private <T extends FlexResponse> boolean hasErrors(ResponseEntity<T> response) {
        return response.getStatusCode() != HttpStatus.OK ||
                response.getBody() == null ||
                !response.getBody().isPopulated();
    }
}
