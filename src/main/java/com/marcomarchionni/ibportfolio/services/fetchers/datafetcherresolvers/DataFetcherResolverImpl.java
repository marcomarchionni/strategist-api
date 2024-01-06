package com.marcomarchionni.ibportfolio.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FileDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.SampleDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.ServerDataFetcher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataFetcherResolverImpl implements DataFetcherResolver {

    private final Map<UpdateContextDto.SourceType, DataFetcher> dataFetcherMap;

    public DataFetcherResolverImpl(FileDataFetcher fileDataFetcher, ServerDataFetcher serverDataFetcher,
                                   SampleDataFetcher sampleDataFetcher) {
        this.dataFetcherMap = Map.of(
                UpdateContextDto.SourceType.FILE, fileDataFetcher,
                UpdateContextDto.SourceType.SERVER, serverDataFetcher,
                UpdateContextDto.SourceType.SAMPLEDATA, sampleDataFetcher
        );
    }

    @Override
    public DataFetcher resolve(UpdateContextDto.SourceType type) {
        DataFetcher dataFetcher = dataFetcherMap.get(type);
        if (dataFetcher == null) throw new IllegalArgumentException("DataFetcher not found for type: " + type);
        return dataFetcher;
    }
}