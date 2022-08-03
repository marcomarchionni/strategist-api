package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Position;
import com.marcomarchionni.ibportfolio.models.dtos.response.PositionListDto;

public interface PositionMapper {

    PositionListDto toPositionListDto(Position position);
}
