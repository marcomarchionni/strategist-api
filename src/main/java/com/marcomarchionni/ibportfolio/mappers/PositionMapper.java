package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionListDto;

public interface PositionMapper {

    PositionListDto toPositionListDto(Position position);

    Position toPosition(FlexQueryResponseDto.OpenPosition openPosition);

    Position mergeIbProperties(Position newPosition, Position existingPosition);
}
