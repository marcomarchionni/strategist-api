package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.dtos.request.PositionFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummaryDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;

import java.util.List;

public interface PositionService {

    List<Position> saveAll(List<Position> positions);

    List<Position> deleteAll(List<Position> positions);

    UpdateReport<Position> updatePositions(List<Position> positions);

    PositionSummaryDto updateStrategyId(UpdateStrategyDto position);

    List<PositionSummaryDto> findByFilter(PositionFindDto positionCriteria);
}
