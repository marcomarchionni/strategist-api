package com.marcomarchionni.ibportfolio.services.fetchers.util;

import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;
import com.marcomarchionni.ibportfolio.services.fetchers.FetchContext;

public interface DataFetcherResolver {

    DataFetcher resolve(FetchContext.SourceType type);
}
