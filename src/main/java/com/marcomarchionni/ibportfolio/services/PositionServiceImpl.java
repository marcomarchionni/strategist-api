package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.util.PositionUpdateManager;
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
    public List<Position> saveAll(List<Position> positions) {
        try {
            return positionRepository.saveAll(positions);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }
    @Override
    public List<Position> deleteAll(List<Position> positions) {
        try {
            positionRepository.deleteAll(positions);
            return positions;
        } catch (Exception e) {
            throw new UnableToDeleteEntitiesException(e.getMessage());
        }
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

        // Create a PositionUpdateManager instance to manage the update process
        PositionUpdateManager updateManager = PositionUpdateManager.createPositionUpdateManager(
                user.getAccountId(), positionMapper, positionRepository);

        // Select positions to add or merge
        List<Position> toAdd = new ArrayList<>();
        List<Position> toMerge = new ArrayList<>();

        for (Position p : positions) {
            if (updateManager.existInDb(p)) {
                toMerge.add(updateManager.getMergedPosition(p));
            } else {
                toAdd.add(p);
            }
        }

        // Select positions to delete
        List<Position> toDelete = updateManager.getUnmergedPositions();

        // Perform database operations and return the result
        return UpdateReport.<Position>builder().added(this.saveAll(toAdd)).merged(this.saveAll(toMerge))
                .deleted(this.deleteAll(toDelete)).build();
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
}


