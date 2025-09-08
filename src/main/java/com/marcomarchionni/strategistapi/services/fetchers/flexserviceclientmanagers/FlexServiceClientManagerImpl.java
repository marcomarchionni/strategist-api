package com.marcomarchionni.strategistapi.services.fetchers.flexserviceclientmanagers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.flex.FlexResponse;
import com.marcomarchionni.strategistapi.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.strategistapi.services.fetchers.flexserviceclients.FlexServiceClient;
import java.util.Objects;
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
  public FlexStatementResponseDto fetchFlexStatementResponseWithRetry(
      String queryId, String token) {
    var statementResponse = flexServiceClient.fetchFlexStatementResponse(queryId, token);

    if (hasErrors(statementResponse)) {
      throw new IbServerErrorException(statementResponse, FlexStatementResponseDto.class);
    }
    return Objects.requireNonNull(statementResponse.getBody());
  }

  @Override
  @Retryable(backoff = @Backoff(delayExpression = "${flexservice.retry-delay}"))
  public FlexQueryResponseDto fetchFlexQueryResponseWithRetry(
      FlexStatementResponseDto statementResponse, String token) {
    var queryResponse = flexServiceClient.fetchFlexQueryResponse(statementResponse, token);

    if (hasErrors(queryResponse)) {
      throw new IbServerErrorException(queryResponse, FlexQueryResponseDto.class);
    }
    return Objects.requireNonNull(queryResponse.getBody());
  }

  private <T extends FlexResponse> boolean hasErrors(ResponseEntity<T> response) {
    if (response.getStatusCode() != HttpStatus.OK) {
      return true;
    }
    T body = response.getBody();
    if (body == null) {
      return true;
    }
    return !body.isPopulated();
  }
}
