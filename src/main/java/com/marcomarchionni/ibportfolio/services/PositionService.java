package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface PositionService {

    List<Position> saveAll(List<Position> positions);

    List<Position> deleteAll(List<Position> positions);

    PositionListDto updateStrategyId(UpdateStrategyDto position);

    List<PositionListDto> findByFilter(PositionFindDto positionCriteria);

    UpdateReport<Position> updatePositions(List<Position> positions);
}
