package com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;

public interface DataFetcherResolver {

    DataFetcher resolve(UpdateContextReq.SourceType type);
}
