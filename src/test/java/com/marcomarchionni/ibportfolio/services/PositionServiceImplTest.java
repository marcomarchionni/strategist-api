package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToSaveEntitiesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePositions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    PositionRepository positionRepository;

    @InjectMocks
    PositionServiceImpl positionService;

    private List<Position> samplePositions;

    @BeforeEach
    void setup() {
        samplePositions = getSamplePositions();
    }

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


}