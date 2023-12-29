package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.PositionAccessService;
import com.marcomarchionni.ibportfolio.accessservice.StrategyAccessService;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.services.util.PositionsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionAccessService positionAccessService;
    private final StrategyAccessService strategyAccessService;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionSummaryDto> findByFilter(PositionFindDto positionFind) {
        List<Position> positions = positionAccessService.findByParams(
                positionFind.getTagged(),
                positionFind.getSymbol(),
                positionFind.getAssetCategory()
        );
        return positions.stream().map(positionMapper::toPositionListDto).collect(Collectors.toList());
    }


    @Override
    public UpdateReport<Position> updatePositions(List<Position> positions) {

        if (positions.isEmpty()) {
            return UpdateReport.<Position>builder().build();
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
        return UpdateReport.<Position>builder()
                .added(this.saveAll(newPositions))
                .merged(this.saveAll(mergedPositions))
                .deleted(this.deleteAll(positionsToDelete))
                .build();
    }

    @Override
    public PositionSummaryDto updateStrategyId(UpdateStrategyDto positionUpdate) {
        Position position = positionAccessService.findById(positionUpdate.getId()).orElseThrow(
                () -> new EntityNotFoundException(Position.class, positionUpdate.getId())
        );
        Strategy strategyToAssign = strategyAccessService.findById(positionUpdate.getStrategyId()).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, positionUpdate.getStrategyId())
        );
        position.setStrategy(strategyToAssign);
        return positionMapper.toPositionListDto(positionAccessService.save(position));
    }

    @Override
    public List<Position> saveAll(List<Position> positions) {
        try {
            return positionAccessService.saveAll(positions);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    @Override
    public List<Position> deleteAll(List<Position> positions) {
        try {
            positionAccessService.deleteAll(positions);
            return positions;
        } catch (Exception e) {
            throw new UnableToDeleteEntitiesException(e.getMessage());
        }
    }
}


