package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;

public interface PositionMapper {

    PositionSummaryDto toPositionListDto(Position position);

    Position toPosition(FlexQueryResponseDto.OpenPosition openPosition);

    Position mergeIbProperties(Position newPosition, Position existingPosition);
}
