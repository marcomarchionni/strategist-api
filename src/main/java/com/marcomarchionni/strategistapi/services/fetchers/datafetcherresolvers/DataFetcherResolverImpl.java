package com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.FileDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.SampleDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.ServerDataFetcher;
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
