package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
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
    public List<Position> findWithParameters(Boolean tagged, String symbol, String assetCategory) {
        if (!StringUtils.hasText(symbol)) {
            symbol = null;
        }
        if (!StringUtils.hasText(assetCategory)) {
            assetCategory = null;
        }
        return positionRepository.findWithParameters(tagged, symbol, assetCategory);
    }

    @Override
    public Position updateStrategyId(Position position) {
        Position positionToUpdate = positionRepository.findById(position.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Trade with id: " + position.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(position.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + position.getStrategyId() + " not found")
        );
        positionToUpdate.setStrategyId(strategyToAssign.getId());
        return positionRepository.save(positionToUpdate);
    }
}
