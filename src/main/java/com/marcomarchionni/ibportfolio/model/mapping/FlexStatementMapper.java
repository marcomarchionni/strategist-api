package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;

public interface FlexStatementMapper {

    FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto);
}
