package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.util.DataFetcherResolver;
import com.marcomarchionni.ibportfolio.services.validators.FlexQueryResponseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UpdateOrchestratorImpl implements UpdateOrchestrator {

    private final DataFetcherResolver dataFetcherResolver;
    private final UpdateService updateService;
    private final FlexQueryResponseValidator flexValidator;

    @Override
    public CombinedUpdateReport update(UpdateContextDto context) throws IOException {
        // Resolve data fetcher
        DataFetcher fetcher = dataFetcherResolver.resolve(context.getSourceType());
        // Fetch dto
        FlexQueryResponseDto dto = fetcher.fetch(context);
        // Validate dto
        flexValidator.isValid(dto);
        // Save dto data to db
        return updateService.update(dto);
    }
}


