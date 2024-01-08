package com.marcomarchionni.strategistapi.services.util;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.FileDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.SampleDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.ServerDataFetcher;
import com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers.DataFetcherResolver;
import com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers.DataFetcherResolverImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DataFetcherResolverImplTest {

    @Mock
    FileDataFetcher fileDataFetcher;

    @Mock
    ServerDataFetcher serverDataFetcher;

    @Mock
    SampleDataFetcher sampleDataFetcher;

    DataFetcherResolver dataFetcherResolver;

    @BeforeEach
    void setUp() {
        dataFetcherResolver = new DataFetcherResolverImpl(fileDataFetcher, serverDataFetcher, sampleDataFetcher);
    }

    @Test
    void resolve() {
        DataFetcher dataFetcher = dataFetcherResolver.resolve(UpdateContextDto.SourceType.FILE);
        assertNotNull(dataFetcher);
        assertInstanceOf(FileDataFetcher.class, dataFetcher);
        dataFetcher = dataFetcherResolver.resolve(UpdateContextDto.SourceType.SERVER);
        assertNotNull(dataFetcher);
        assertInstanceOf(ServerDataFetcher.class, dataFetcher);
    }
}