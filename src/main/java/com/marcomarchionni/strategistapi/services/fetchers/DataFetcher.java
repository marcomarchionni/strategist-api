package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;

public interface DataFetcher {
    FlexQueryResponseDto fetch(UpdateContext context);
}
