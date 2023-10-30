package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    private Position copyAllPropertiesButStrategy(Position newPosition, Position existingPosition) {
            existingPosition.setConId(newPosition.getConId());
            existingPosition.setReportDate(newPosition.getReportDate());
            existingPosition.setSymbol(newPosition.getSymbol());
            existingPosition.setDescription(newPosition.getDescription());
            existingPosition.setAssetCategory(newPosition.getAssetCategory());
            existingPosition.setPutCall(newPosition.getPutCall());
            existingPosition.setStrike(newPosition.getStrike());
            existingPosition.setExpiry(newPosition.getExpiry());
            existingPosition.setQuantity(newPosition.getQuantity());
            existingPosition.setCostBasisPrice(newPosition.getCostBasisPrice());
            existingPosition.setCostBasisMoney(newPosition.getCostBasisMoney());
            existingPosition.setMarkPrice(newPosition.getMarkPrice());
            existingPosition.setMultiplier(newPosition.getMultiplier());
            existingPosition.setPositionValue(newPosition.getPositionValue());
            existingPosition.setFifoPnlUnrealized(newPosition.getFifoPnlUnrealized());
            return existingPosition;
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
    public List<PositionListDto> findByFilter(PositionFindDto positionFind) {
        List<Position> positions = positionRepository.findByParams(
                                            positionFind.getTagged(),
                                            positionFind.getSymbol(),
                                            positionFind.getAssetCategory()
        );
        return positions.stream().map(positionMapper::toPositionListDto).collect(Collectors.toList());
    }


    @Override
    public UpdateReport<Position> updatePositions(List<Position> newPositions) {
        List<Position> existingPositions = positionRepository.findAll();

        // Create a map of existing positions
        Map<Long, Position> existingPositionsMap = existingPositions.stream()
                .collect(Collectors.toMap(Position::getId, Function.identity()));

        // Create a map of new positions
        Map<Long, Position> newPositionsMap = newPositions.stream()
                .collect(Collectors.toMap(Position::getId, Function.identity()));

        // Select positions to be saved
        List<Position> toAdd = newPositions.stream()
                .filter(newPosition -> !existingPositionsMap.containsKey(newPosition.getId()))
                .toList();

        // Select positions to be merged
        List<Position> toMerge = newPositions.stream()
                .filter(newPosition -> existingPositionsMap.containsKey(newPosition.getId()))
                .map(newPosition -> this.copyAllPropertiesButStrategy(newPosition,
                        existingPositionsMap.get(newPosition.getId())))
                .toList();

        // Select positions to be deleted
        List<Position> toDelete = existingPositions.stream()
                .filter(existingPosition -> !newPositionsMap.containsKey(existingPosition.getId()))
                .toList();

        // Perform database operations and return the result
        return UpdateReport.<Position>builder().added(this.saveAll(toAdd)).merged(this.saveAll(toMerge))
                .deleted(this.deleteAll(toDelete)).build();
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


