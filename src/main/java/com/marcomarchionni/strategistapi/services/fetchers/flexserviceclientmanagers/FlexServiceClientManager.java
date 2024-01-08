package com.marcomarchionni.strategistapi.services.fetchers.flexserviceclientmanagers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.flex.FlexStatementResponseDto;

public interface FlexServiceClientManager {

    FlexStatementResponseDto fetchFlexStatementResponseWithRetry(String queryId, String token);

    FlexQueryResponseDto fetchFlexQueryResponseWithRetry(FlexStatementResponseDto statementResponse, String token);
}
