package com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;

public interface DataFetcherResolver {

    DataFetcher resolve(UpdateContext.SourceType type);
}
