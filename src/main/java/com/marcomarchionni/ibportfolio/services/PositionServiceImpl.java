package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.util.PositionsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;
    private final StrategyRepository strategyRepository;
    private final PositionMapper positionMapper;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, StrategyRepository strategyRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.strategyRepository = strategyRepository;
        this.positionMapper = positionMapper;
    }

    @Override
    public List<PositionSummaryDto> findByFilter(PositionFindDto positionFind) {
        List<Position> positions = positionRepository.findByParams(
                                            positionFind.getTagged(),
                                            positionFind.getSymbol(),
                                            positionFind.getAssetCategory()
        );
        return positions.stream().map(positionMapper::toPositionListDto).collect(Collectors.toList());
    }


    @Override
    public UpdateReport<Position> updatePositions(User user, List<Position> positions) {

        if (positions.isEmpty()) {
            return UpdateReport.<Position>builder().build();
        }

        // Create a DbPositionsCache to map positions in db
        List<Position> dbPositions = positionRepository.findAllByAccountId(user.getAccountId());
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
                .added(this.saveAll(user, newPositions))
                .merged(this.saveAll(user, mergedPositions))
                .deleted(this.deleteAll(user, positionsToDelete))
                .build();
    }

    @Override
    public PositionSummaryDto updateStrategyId(UpdateStrategyDto positionUpdate) {
        Position position = positionRepository.findById(positionUpdate.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Position with id: " + positionUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(positionUpdate.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + positionUpdate.getStrategyId() + " not found")
        );
        position.setStrategy(strategyToAssign);
        return positionMapper.toPositionListDto(positionRepository.save(position));
    }

    @Override
    public List<Position> saveAll(User user, List<Position> positions) {
        validateUser(user, positions);
        try {
            return positionRepository.saveAll(positions);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    @Override
    public List<Position> deleteAll(User user, List<Position> positions) {
        validateUser(user, positions);
        try {
            positionRepository.deleteAll(positions);
            return positions;
        } catch (Exception e) {
            throw new UnableToDeleteEntitiesException(e.getMessage());
        }
    }

    private void validateUser(User user, List<Position> positions) {
        if (!positions.stream().allMatch(p -> p.getAccountId().equals(user.getAccountId()))) {
            throw new InvalidUserDataException();
        }
    }
}


