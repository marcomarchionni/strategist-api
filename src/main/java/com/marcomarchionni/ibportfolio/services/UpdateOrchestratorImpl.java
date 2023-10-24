package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FetchContext;
import com.marcomarchionni.ibportfolio.services.util.DataFetcherResolver;
import com.marcomarchionni.ibportfolio.services.util.DataSourceType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class UpdateOrchestratorImpl implements UpdateOrchestrator {

    private final DataFetcherResolver dataFetcherResolver;
    private final DataSaverService dataSaverService;

    public UpdateOrchestratorImpl(DataFetcherResolver dataFetcherResolver, DataSaverService dataSaverService) {
        this.dataFetcherResolver = dataFetcherResolver;
        this.dataSaverService = dataSaverService;
    }

    @Override
    public void updateFromServer() throws IOException {
        DataFetcher dataFetcher = dataFetcherResolver.resolve(DataSourceType.SERVER);
        FlexQueryResponseDto dto = dataFetcher.fetch(new FetchContext());
        dataSaverService.save(dto);

    }

    @Override
    public void updateFromFile(InputStream stream) throws IOException {
        DataFetcher dataFetcher = dataFetcherResolver.resolve(DataSourceType.FILE);
        FetchContext context = new FetchContext();
        context.setStream(stream);
        FlexQueryResponseDto dto = dataFetcher.fetch(context);
        dataSaverService.save(dto);

    }
}
