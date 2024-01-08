package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;

public interface FlexStatementMapper {

    FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto);
}
