package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PositionAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.PositionFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.PositionMapper;
import com.marcomarchionni.strategistapi.services.util.PositionsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionAccessService positionAccessService;
    private final StrategyAccessService strategyAccessService;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionSummary> findByFilter(PositionFind positionFind) {
        List<Position> positions = positionAccessService.findByParams(
                positionFind.getTagged(),
                positionFind.getSymbol(),
                positionFind.getAssetCategory()
        );
        return positions.stream().map(positionMapper::toPositionSummary).collect(Collectors.toList());
    }


    @Override
    public UpdateReport<PositionSummary> updatePositions(List<Position> positions) {

        if (positions.isEmpty()) {
            return UpdateReport.<PositionSummary>builder().build();
        }

        // Create a DbPositionsCache to map positions in db
        List<Position> dbPositions = positionAccessService.findAll();
        PositionsCache cache = PositionsCache.createPositionsCache(dbPositions);

        // Select positions to add or merge
        List<Position> newPositions = new ArrayList<>();
        List<Position> mergedPositions = new ArrayList<>();

        for (Position position : positions) {
            if (cache.existMatch(position)) {
                Position positionToMerge = cache.extractMatchingPosition(position);
                mergedPositions.add(positionMapper.mergeFlexProperties(position, positionToMerge));
            } else {
                newPositions.add(position);
            }
        }

        // Select positions to delete
        List<Position> positionsToDelete = cache.getUnmatchedPositions();

        // Perform database operations and return the result
        return UpdateReport.<PositionSummary>builder()
                .added(this.saveAll(newPositions))
                .merged(this.saveAll(mergedPositions))
                .deleted(this.deleteAll(positionsToDelete))
                .build();
    }

    @Override
    public PositionSummary updateStrategyId(StrategyAssign positionUpdate) {
        Position position = positionAccessService.findById(positionUpdate.getId()).orElseThrow(
                () -> new EntityNotFoundException(Position.class, positionUpdate.getId())
        );
        Strategy strategyToAssign = getStrategyToAssign(positionUpdate.getStrategyId());
        position.setStrategy(strategyToAssign);
        return positionMapper.toPositionSummary(positionAccessService.save(position));
    }

    private Strategy getStrategyToAssign(Long strategyId) {
        return Optional.ofNullable(strategyId)
                .map(id -> strategyAccessService.findById(strategyId).orElseThrow(
                        () -> new EntityNotFoundException(Strategy.class, strategyId)))
                .orElse(null);
    }

    @Override
    public List<PositionSummary> saveAll(List<Position> positions) {
        try {
            List<Position> savedPositions = positionAccessService.saveAll(positions);
            return savedPositions.stream().map(positionMapper::toPositionSummary).toList();
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    @Override
    public List<PositionSummary> deleteAll(List<Position> positions) {
        try {
            positionAccessService.deleteAll(positions);
            return positions.stream().map(positionMapper::toPositionSummary).toList();
        } catch (Exception e) {
            throw new UnableToDeleteEntitiesException(e.getMessage());
        }
    }
}


