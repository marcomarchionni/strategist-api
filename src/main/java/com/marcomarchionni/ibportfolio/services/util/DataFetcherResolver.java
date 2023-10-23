package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;

public interface DataFetcherResolver {

    DataFetcher resolve(DataSourceType type);
}
