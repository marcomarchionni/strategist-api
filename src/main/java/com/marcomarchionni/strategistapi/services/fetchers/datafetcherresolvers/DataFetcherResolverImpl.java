package com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.FileDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.SampleDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.ServerDataFetcher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFetcherResolverImpl implements DataFetcherResolver {

    private final Map<UpdateContext.SourceType, DataFetcher> dataFetcherMap;

    public DataFetcherResolverImpl(FileDataFetcher fileDataFetcher, ServerDataFetcher serverDataFetcher,
                                   SampleDataFetcher sampleDataFetcher) {
        this.dataFetcherMap = Map.of(
                UpdateContext.SourceType.FILE, fileDataFetcher,
                UpdateContext.SourceType.SERVER, serverDataFetcher,
                UpdateContext.SourceType.SAMPLEDATA, sampleDataFetcher
        );
    }

    @Override
    public DataFetcher resolve(UpdateContext.SourceType type) {
        DataFetcher dataFetcher = dataFetcherMap.get(type);
        if (dataFetcher == null) throw new IllegalArgumentException("DataFetcher not found for type: " + type);
        return dataFetcher;
    }
}
