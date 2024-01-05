package com.marcomarchionni.ibportfolio.services.fetchers.datafetcherresolvers;

import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.services.fetchers.DataFetcher;

public interface DataFetcherResolver {

    DataFetcher resolve(UpdateContextDto.SourceType type);
}
