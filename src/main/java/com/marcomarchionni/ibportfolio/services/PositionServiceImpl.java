package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapper;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<PositionListDto> findByFilter(PositionFindDto positionFind) {
        List<Position> positions = positionRepository.findByParams(
                                            positionFind.getTagged(),
                                            positionFind.getSymbol(),
                                            positionFind.getAssetCategory()
        );
        return positions.stream().map(positionMapper::toPositionListDto).collect(Collectors.toList());
    }

    @Override
    public PositionListDto updateStrategyId(UpdateStrategyDto positionUpdate) {
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
