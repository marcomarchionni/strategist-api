package com.marcomarchionni.strategistapi.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.services.fetchers.DataFetcher;

public interface DataFetcherResolver {

    DataFetcher resolve(UpdateContextDto.SourceType type);
}
