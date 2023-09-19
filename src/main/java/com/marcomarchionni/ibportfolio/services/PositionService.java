package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;

import java.time.LocalDate;
import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll(List<Position> positions);

    PositionListDto updateStrategyId(UpdateStrategyDto position);

    List<PositionListDto> findByFilter(PositionFindDto positionCriteria);

    void updatePositions(List<Position> positions);
}
