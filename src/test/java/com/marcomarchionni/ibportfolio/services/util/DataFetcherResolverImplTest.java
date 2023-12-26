package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FetchContext;
import com.marcomarchionni.ibportfolio.services.fetchers.FileDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.ServerDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.util.DataFetcherResolver;
import com.marcomarchionni.ibportfolio.services.fetchers.util.DataFetcherResolverImpl;
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

    DataFetcherResolver dataFetcherResolver;

    @BeforeEach
    void setUp() {
        dataFetcherResolver = new DataFetcherResolverImpl(fileDataFetcher, serverDataFetcher);
    }

    @Test
    void resolve() {
        DataFetcher dataFetcher = dataFetcherResolver.resolve(FetchContext.SourceType.FILE);
        assertNotNull(dataFetcher);
        assertInstanceOf(FileDataFetcher.class, dataFetcher);
        dataFetcher = dataFetcherResolver.resolve(FetchContext.SourceType.SERVER);
        assertNotNull(dataFetcher);
        assertInstanceOf(ServerDataFetcher.class, dataFetcher);
    }
}