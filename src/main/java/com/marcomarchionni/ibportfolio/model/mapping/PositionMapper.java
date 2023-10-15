package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;

public interface PositionMapper {

    PositionListDto toPositionListDto(Position position);

    Position toPosition(FlexQueryResponseDto.OpenPosition openPosition);
}
