package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.dtos.PositionCriteriaDto;

import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll();

    Position updateStrategyId(Position position);

    List<Position> findWithCriteria(PositionCriteriaDto positionCriteria);
}
