package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;

public interface DataFetcher {
    FlexQueryResponseDto fetch(UpdateContextDto context);
}
