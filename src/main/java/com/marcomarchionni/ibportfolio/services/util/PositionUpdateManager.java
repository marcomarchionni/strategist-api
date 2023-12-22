package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PositionUpdateManager {
    private final Map<Long, Position> openPositionsMap;
    private final PositionMapper mapper;
    private final Map<Long, Position> unmergedPositions;

    private PositionUpdateManager(PositionMapper mapper, Map<Long, Position> openPositionsMap) {
        this.mapper = mapper;
        this.openPositionsMap = openPositionsMap;
        this.unmergedPositions = new HashMap<>(openPositionsMap);
    }

    public static PositionUpdateManager createPositionUpdateManager(String accountId, PositionMapper mapper,
                                                                    PositionRepository positionRepository) {
        Map<Long, Position> openPositionsMap = positionRepository.findAllByAccountId(accountId).stream()
                .collect(Collectors.toMap(Position::getConId, Function.identity()));
        return new PositionUpdateManager(mapper, openPositionsMap);
    }

    public boolean existInDb(Position position) {
        return openPositionsMap.containsKey(position.getConId());
    }

    public Position getMergedPosition(Position source) {
        unmergedPositions.remove(source.getConId());
        Position target = openPositionsMap.get(source.getConId());
        mapper.mergeFlexProperties(source, target);
        return target;
    }

    public List<Position> getUnmergedPositions() {
        return new ArrayList<>(unmergedPositions.values());
    }
}
