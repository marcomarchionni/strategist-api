package com.marcomarchionni.ibportfolio.services.fetchers.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FetchContext;
import com.marcomarchionni.ibportfolio.services.fetchers.FileDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.ServerDataFetcher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFetcherResolverImpl implements DataFetcherResolver {

    private final Map<FetchContext.SourceType, DataFetcher> dataFetcherMap;

    public DataFetcherResolverImpl(FileDataFetcher fileDataFetcher, ServerDataFetcher serverDataFetcher) {
        this.dataFetcherMap = Map.of(
                FetchContext.SourceType.FILE, fileDataFetcher,
                FetchContext.SourceType.SERVER, serverDataFetcher
        );
    }

    @Override
    public DataFetcher resolve(FetchContext.SourceType type) {
        DataFetcher dataFetcher = dataFetcherMap.get(type);
        if (dataFetcher == null) throw new IllegalArgumentException("DataFetcher not found for type: " + type);
        return dataFetcher;
    }
}
