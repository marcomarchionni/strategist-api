package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionListDto;

import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll(List<Position> positions);

    PositionListDto updateStrategyId(UpdateStrategyDto position);

    List<PositionListDto> findByFilter(PositionFindDto positionCriteria);

    void updatePositions(List<Position> positions);
}
