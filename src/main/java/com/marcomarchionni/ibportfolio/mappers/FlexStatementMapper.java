package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;

public interface FlexStatementMapper {

    FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto);
}
