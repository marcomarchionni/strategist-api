package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapper;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    PositionRepository positionRepository;

    @Mock
    StrategyRepository strategyRepository;

    PositionMapper positionMapper;

    PositionServiceImpl positionService;

    List<Position> samplePositions;
    Position samplePosition;
    Strategy sampleStrategy;
    PositionFindDto positionFind;

    @BeforeEach
    void setup() {
        samplePositions = getSamplePositions();
        samplePosition = getSamplePosition();
        sampleStrategy = getSampleStrategy();
        positionFind = getSamplePositionCriteria();
        positionMapper = new PositionMapperImpl(new ModelMapper());
        positionService = new PositionServiceImpl(positionRepository, strategyRepository, positionMapper);
    }

    @Test
    void saveAll() {
        assertDoesNotThrow(() -> positionService.saveAll(samplePositions));
    }

    @Test
    void saveAllException() {
        doThrow(new RuntimeException()).when(positionRepository).saveAll(any());

        assertThrows(UnableToSaveEntitiesException.class, () -> positionService.saveAll(samplePositions));
    }

    @Test
    void deleteAllPositions() {
        assertDoesNotThrow(() -> positionService.deleteAll(samplePositions));
    }

    @Test
    void deleteAllPositionsException() {
        doThrow(new RuntimeException()).when(positionRepository).deleteAll();

        assertThrows(UnableToDeleteEntitiesException.class, () -> positionService.deleteAll(List.of(getAMZNPosition())));
    }

    @Test
    void findWithParameters() {
        when(positionRepository.findByParams(any(), any(), any())).thenReturn(samplePositions);

        List<PositionListDto> positions = positionService.findByFilter(positionFind);

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

        PositionListDto actualPositionListDto = positionService.updateStrategyId(positionUpdate);

        assertNotNull(actualPositionListDto);
        assertEquals(samplePosition.getId(), actualPositionListDto.getId());
        assertEquals(sampleStrategy.getId(), actualPositionListDto.getStrategyId());
    }

    @Test
    void updatePositionsTest() {
        // data setup
        Position existingADYENposition = getADYENPosition();
        existingADYENposition.setReportDate(LocalDate.of(2022, 6, 30));
        existingADYENposition.setQuantity(BigDecimal.valueOf(1));
        existingADYENposition.setStrategy(sampleStrategy);

        Position existingADBEposition = getADBEPosition();
        existingADBEposition.setReportDate(LocalDate.of(2022, 6, 30));

        Position newADYENposition = getADYENPosition();
        newADYENposition.setReportDate(LocalDate.of(2022, 7, 7));
        newADYENposition.setQuantity(BigDecimal.valueOf(2));

        Position newAMZNposition = getAMZNPosition();
        newAMZNposition.setReportDate(LocalDate.of(2022, 7, 7));

        List<Position> existingPositions = List.of(existingADYENposition, existingADBEposition);
        List<Position> newPositions = List.of(newADYENposition, newAMZNposition);

        // init captors
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Position>> saveCaptor = ArgumentCaptor.forClass(List.class);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Position>> deleteCaptor = ArgumentCaptor.forClass(List.class);

        when(positionRepository.findAll()).thenReturn(existingPositions);

        // test
        positionService.updatePositions(newPositions);

        verify(positionRepository).saveAll(saveCaptor.capture());
        verify(positionRepository).deleteAll(deleteCaptor.capture());

        List<Position> saved = saveCaptor.getValue();
        List<Position> deleted = deleteCaptor.getValue();

        assertEquals(2, saved.size());
        assertEquals(1, deleted.size());
        assertEquals(saved.get(0).getSymbol(), "ADYEN");
        assertEquals(saved.get(0).getReportDate(), LocalDate.of(2022, 7, 7));
        assertEquals(saved.get(0).getQuantity(), BigDecimal.valueOf(2));
        assertEquals(saved.get(0).getStrategy(), sampleStrategy);

        assertEquals(saved.get(1).getSymbol(), "AMZN");

        assertEquals(deleted.get(0).getSymbol(), "ADBE");
        assert deleted.contains(existingADBEposition);
    }
}
