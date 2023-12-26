package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;

import java.io.IOException;

public interface DataFetcher {
    FlexQueryResponseDto fetch(FetchContext context) throws IOException;
}
