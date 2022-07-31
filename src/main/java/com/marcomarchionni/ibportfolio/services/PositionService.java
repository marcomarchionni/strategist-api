package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.PositionFindDto;

import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll();

    Position updateStrategyId(UpdateStrategyDto position);

    List<Position> findByParams(PositionFindDto positionCriteria);
}
