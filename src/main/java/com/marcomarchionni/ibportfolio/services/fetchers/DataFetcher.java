package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;

public interface DataFetcher {
    FlexQueryResponseDto fetch(UpdateContextDto context);
}
