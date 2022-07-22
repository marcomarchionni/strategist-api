package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;

import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll();

    List<Position> findWithParameters(Boolean tagged, String symbol, String assetCategory);

    Position updateStrategyId(Position position);
}
