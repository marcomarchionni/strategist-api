package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FileDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.ServerDataFetcher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFetcherResolverImpl implements DataFetcherResolver {

    private final Map<DataSourceType, DataFetcher> dataFetcherMap;

    public DataFetcherResolverImpl(FileDataFetcher fileDataFetcher, ServerDataFetcher serverDataFetcher) {
        this.dataFetcherMap = Map.of(
                DataSourceType.FILE, fileDataFetcher,
                DataSourceType.SERVER, serverDataFetcher
        );
    }

    @Override
    public DataFetcher resolve(DataSourceType type) {
        DataFetcher dataFetcher = dataFetcherMap.get(type);
        if (dataFetcher == null) throw new IllegalArgumentException("DataFetcher not found for type: " + type);
        return dataFetcher;
    }
}
