package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.PositionFindDto;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    PositionRepository positionRepository;

    @Mock
    StrategyRepository strategyRepository;

    @InjectMocks
    PositionServiceImpl positionService;

    final List<Position> samplePositions = getSamplePositions();
    final Position samplePosition = getSamplePosition();
    final Strategy sampleStrategy = getSampleStrategy();
    final PositionFindDto positionCriteria = getSamplePositionCriteria();

    @Test
    void saveAll() {
        assertDoesNotThrow(() -> positionService.saveAll(samplePositions));
    }

    @Test
    void saveAllException() {
        doThrow(new RuntimeException()).when(positionRepository).saveAll(any());

        assertThrows(UnableToSaveEntitiesException.class, ()-> positionService.saveAll(samplePositions));
    }

    @Test
    void deleteAllPositions() {
        assertDoesNotThrow(() -> positionService.deleteAll());
    }

    @Test
    void deleteAllPositionsException() {
        doThrow(new RuntimeException()).when(positionRepository).deleteAll();

        assertThrows(UnableToDeleteEntitiesException.class, ()-> positionService.deleteAll());
    }

    @Test
    void findWithParameters() {
        when(positionRepository.findWithParameters(any(), any(), any())).thenReturn(samplePositions);

        List<Position> positions = positionService.findByParams(positionCriteria);

        assertNotNull(positions);
        assertEquals(positions.size(), samplePositions.size());
    }

    @Test
    void updateStrategyId() {
        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder()
                .id(samplePosition.getId()).strategyId(sampleStrategy.getId()).build();

        when(positionRepository.findById(any())).thenReturn(Optional.of(samplePosition));
        when(strategyRepository.findById(any())).thenReturn(Optional.of(sampleStrategy));
        samplePosition.setStrategy(sampleStrategy);
        when(positionRepository.save(any())).thenReturn(samplePosition);

        Position actualPosition = positionService.updateStrategyId(positionUpdate);

        assertNotNull(actualPosition);
        assertEquals(samplePosition.getId(), actualPosition.getId());
        assertEquals(sampleStrategy.getId(), actualPosition.getStrategy().getId());
    }
}