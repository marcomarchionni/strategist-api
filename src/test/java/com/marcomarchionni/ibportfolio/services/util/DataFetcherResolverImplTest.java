package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FileDataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.ServerDataFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        DataFetcher dataFetcher = dataFetcherResolver.resolve(DataSourceType.FILE);
        assertNotNull(dataFetcher);
        assertTrue(dataFetcher instanceof FileDataFetcher);
        dataFetcher = dataFetcherResolver.resolve(DataSourceType.SERVER);
        assertNotNull(dataFetcher);
        assertTrue(dataFetcher instanceof ServerDataFetcher);
    }
}