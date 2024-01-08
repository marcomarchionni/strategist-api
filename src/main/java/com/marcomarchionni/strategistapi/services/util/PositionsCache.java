package com.marcomarchionni.strategistapi.services.util;

import com.marcomarchionni.strategistapi.domain.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PositionsCache {
    private final Map<Long, Position> positionsMap;
    private final Map<Long, Position> unmatchedPositions;

    private PositionsCache(Map<Long, Position> positionsMap) {
        this.positionsMap = positionsMap;
        this.unmatchedPositions = new HashMap<>(positionsMap);
    }

    public static PositionsCache createPositionsCache(List<Position> dbPositions) {
        Map<Long, Position> dbPositionsMap = dbPositions.stream()
                .collect(Collectors.toMap(Position::getConId, Function.identity()));
        return new PositionsCache(dbPositionsMap);
    }

    public boolean existMatch(Position position) {
        return positionsMap.containsKey(position.getConId());
    }

    public Position extractMatchingPosition(Position source) {
        unmatchedPositions.remove(source.getConId());
        return positionsMap.get(source.getConId());
    }

    public List<Position> getUnmatchedPositions() {
        return new ArrayList<>(unmatchedPositions.values());
    }
}
