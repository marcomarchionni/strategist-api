package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;

public interface PositionMapper {

    PositionSummary toPositionListDto(Position position);

    Position toPosition(FlexQueryResponseDto.OpenPosition openPosition);

    Position mergeFlexProperties(Position newPosition, Position existingPosition);
}
