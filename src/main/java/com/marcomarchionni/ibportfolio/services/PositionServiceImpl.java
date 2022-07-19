package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToSaveEntitiesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
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
}
