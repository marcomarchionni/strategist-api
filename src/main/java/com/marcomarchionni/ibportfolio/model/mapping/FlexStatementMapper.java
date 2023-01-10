package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;

public interface FlexStatementMapper {

    FlexStatement toFlexStatement(FlexQueryResponse.FlexStatement flexStatementDto);
}
