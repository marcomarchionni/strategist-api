package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;

import java.io.IOException;

public interface DataFetcher {
    FlexQueryResponseDto fetch(UpdateContextDto context) throws IOException;
}
