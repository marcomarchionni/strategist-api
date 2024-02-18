package com.marcomarchionni.strategistapi.services.fetchers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;

public interface DataFetcher {
    FlexQueryResponseDto fetch(UpdateContextReq context);
}
