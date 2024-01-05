package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.services.fetchers.util.FlexServiceClientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerDataFetcher implements DataFetcher {
    private final FlexServiceClientManager clientManager;

    @Override
    public FlexQueryResponseDto fetch(UpdateContextDto context) {
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

