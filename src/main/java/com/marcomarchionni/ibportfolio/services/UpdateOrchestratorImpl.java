package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FetchContext;
import com.marcomarchionni.ibportfolio.services.fetchers.util.DataFetcherResolver;
import com.marcomarchionni.ibportfolio.services.validators.FlexQueryResponseValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UpdateOrchestratorImpl implements UpdateOrchestrator {

    private final DataFetcherResolver dataFetcherResolver;
    private final UpdateService updateService;
    private final FlexQueryResponseValidator flexValidator;

    public UpdateOrchestratorImpl(DataFetcherResolver dataFetcherResolver, UpdateService updateService,
                                  FlexQueryResponseValidator flexValidator) {
        this.dataFetcherResolver = dataFetcherResolver;
        this.updateService = updateService;
        this.flexValidator = flexValidator;
    }

    @Override
    public CombinedUpdateReport updateFromServer(User user) throws IOException {
        var context = FetchContext.builder().sourceType(FetchContext.SourceType.SERVER).build();
        return this.update(user, context);
    }

    @Override
    public CombinedUpdateReport updateFromFile(User user, MultipartFile file) throws IOException {
        var context = FetchContext.builder().sourceType(FetchContext.SourceType.FILE).file(file).build();
        return this.update(user, context);
    }

    private CombinedUpdateReport update(User user, FetchContext context) throws IOException {
        // Resolve data fetcher
        DataFetcher fetcher = dataFetcherResolver.resolve(context.getSourceType());
        // Fetch dto
        FlexQueryResponseDto dto = fetcher.fetch(context);
        // Validate dto
        flexValidator.isValid(dto);
        // Save dto data to db
        return updateService.update(user, dto);
    }
}


