package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers.DataFetcherResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This class is responsible for orchestrating the update process.
 */
@Service
@RequiredArgsConstructor
public class UpdateOrchestratorImpl implements UpdateOrchestrator {

    private final DataFetcherResolver dataFetcherResolver;
    private final UpdateService updateService;

    @Override
    public CombinedUpdateReport update(UpdateContextReq context) throws IOException {
        // Resolve data fetcher
        DataFetcher fetcher = dataFetcherResolver.resolve(context.getSourceType());
        // Fetch dto
        FlexQueryResponseDto dto = fetcher.fetch(context);
        // Save dto data to db
        return updateService.update(dto);
    }
}


