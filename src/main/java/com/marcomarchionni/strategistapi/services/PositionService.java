package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.dtos.request.PositionFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;

import java.util.List;

public interface PositionService {

    List<PositionSummary> saveAll(List<Position> positions);

    List<PositionSummary> deleteAll(List<Position> positions);

    UpdateReport<PositionSummary> updatePositions(List<Position> positions);

    PositionSummary updateStrategyId(StrategyAssign position);

    List<PositionSummary> findByFilter(PositionFind positionCriteria);
}
