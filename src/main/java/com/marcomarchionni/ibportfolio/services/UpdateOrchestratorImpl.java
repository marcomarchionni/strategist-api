package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.util.DataFetcherResolver;
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
    public CombinedUpdateReport update(UpdateContextDto context) throws IOException {
        // Resolve data fetcher
        DataFetcher fetcher = dataFetcherResolver.resolve(context.getSourceType());
        // Fetch dto
        FlexQueryResponseDto dto = fetcher.fetch(context);
        // Save dto data to db
        return updateService.update(dto);
    }
}


