package com.marcomarchionni.ibportfolio.services.parsers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateDto;

public interface ResponseParser {
    UpdateDto parseAllData(FlexQueryResponseDto dto);

    UpdateDto parseHistoricalData(FlexQueryResponseDto dto);
}
