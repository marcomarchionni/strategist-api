package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;

public interface UpdateService {
    CombinedUpdateReport update(FlexQueryResponseDto dto);
}
