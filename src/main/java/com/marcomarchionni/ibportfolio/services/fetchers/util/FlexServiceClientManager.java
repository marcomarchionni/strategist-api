package com.marcomarchionni.ibportfolio.services.fetchers.util;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;

public interface FlexServiceClientManager {

    FlexStatementResponseDto fetchFlexStatementResponseWithRetry(String queryId, String token);

    FlexQueryResponseDto fetchFlexQueryResponseWithRetry(FlexStatementResponseDto statementResponse, String token);
}
