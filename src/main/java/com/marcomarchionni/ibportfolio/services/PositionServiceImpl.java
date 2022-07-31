package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.PositionFindDto;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;
    private final StrategyRepository strategyRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, StrategyRepository strategyRepository) {
        this.positionRepository = positionRepository;
        this.strategyRepository = strategyRepository;
    }

    @Override
    public void saveAll(List<Position> positions) {
        try {
            positionRepository.saveAll(positions);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            positionRepository.deleteAll();
        } catch (Exception e) {
            throw new UnableToDeleteEntitiesException(e.getMessage());
        }
    }

    @Override
    public List<Position> findByParams(PositionFindDto positionCriteria) {
        return positionRepository.findWithParameters(
                positionCriteria.getTagged(),
                positionCriteria.getSymbol(),
                positionCriteria.getAssetCategory());
    }

    @Override
    public Position updateStrategyId(UpdateStrategyDto positionUpdate) {
        Position position = positionRepository.findById(positionUpdate.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Position with id: " + positionUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(positionUpdate.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + positionUpdate.getStrategyId() + " not found")
        );
        position.setStrategy(strategyToAssign);
        return positionRepository.save(position);
    }
}
