package com.marcomarchionni.strategistapi.services.parsers;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateDto;

public interface ResponseParser {
    UpdateDto parseAllData(FlexQueryResponseDto dto);

    UpdateDto parseHistoricalData(FlexQueryResponseDto dto);
}
