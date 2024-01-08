package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Position;

import java.util.List;
import java.util.Optional;

public interface PositionAccessService {
    void deleteAll(List<Position> positions);

    List<Position> saveAll(List<Position> positions);

    List<Position> findByParams(Boolean tagged, String symbol, String assetCategory);

    List<Position> findAll();

    Optional<Position> findBySymbol(String symbol);

    Optional<Position> findById(Long id);

    Position save(Position position);
}
