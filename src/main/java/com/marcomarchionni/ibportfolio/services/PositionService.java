package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface PositionService {

    List<Position> saveAll(User user, List<Position> positions);

    List<Position> deleteAll(User user, List<Position> positions);

    UpdateReport<Position> updatePositions(User user, List<Position> positions);

    PositionSummaryDto updateStrategyId(UpdateStrategyDto position);

    List<PositionSummaryDto> findByFilter(PositionFindDto positionCriteria);
}
