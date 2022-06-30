package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;

import java.util.List;

public interface PositionService {

    boolean savePositions(List<Position> positions);
    boolean deleteAllPositions();
}
