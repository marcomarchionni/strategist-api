package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.services.fetchers.flexserviceclientmanagers.FlexServiceClientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerDataFetcher implements DataFetcher {
    private final FlexServiceClientManager clientManager;

    @Override
    public FlexQueryResponseDto fetch(UpdateContextReq context) {
        // Extract token and query id from context
        String token = context.getToken();
        String queryId = context.getQueryId();

        // Fetch flex statement response dto using token and query id
        FlexStatementResponseDto flexStatementResponseDto = clientManager.fetchFlexStatementResponseWithRetry(queryId
                , token);

        // Fetch flex query response dto using url and code from first response
        return clientManager.fetchFlexQueryResponseWithRetry(flexStatementResponseDto, token);
    }
}

