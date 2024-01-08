package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PositionAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.PositionFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummaryDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.PositionMapper;
import com.marcomarchionni.strategistapi.mappers.PositionMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    PositionAccessService positionAccessService;

    @Mock
    StrategyAccessService strategyAccessService;

    PositionMapper positionMapper;

    PositionServiceImpl positionService;

    List<Position> samplePositions;
    Position samplePosition;
    Strategy sampleStrategy;
    PositionFindDto positionFind;
    User user;

    @BeforeEach
    void setup() {
        user = getSampleUser();
        samplePositions = getSamplePositions();
        samplePosition = getSamplePosition();
        sampleStrategy = getSampleStrategy();
        positionFind = getSamplePositionCriteria();
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        positionMapper = new PositionMapperImpl(mapper);
        positionService = new PositionServiceImpl(positionAccessService, strategyAccessService, positionMapper);
    }

    @Test
    void saveAll() {
        assertDoesNotThrow(() -> positionService.saveAll(samplePositions));
    }

    @Test
    void saveAllException() {
        doThrow(new RuntimeException()).when(positionAccessService).saveAll(any());

        assertThrows(UnableToSaveEntitiesException.class, () -> positionService.saveAll(samplePositions));
    }

    @Test
    void deleteAllPositions() {
        assertDoesNotThrow(() -> positionService.deleteAll(samplePositions));
    }

    @Test
    void deleteAllPositionsException() {
        doThrow(new RuntimeException()).when(positionAccessService).deleteAll(anyList());

        assertThrows(UnableToDeleteEntitiesException.class,
                () -> positionService.deleteAll(List.of(getAMZNPosition())));
    }

    @Test
    void findWithParameters() {
        when(positionAccessService.findByParams(any(), any(), any())).thenReturn(samplePositions);

        List<PositionSummaryDto> positions = positionService.findByFilter(positionFind);

        assertNotNull(positions);
        assertEquals(positions.size(), samplePositions.size());
    }

    @Test
    void updateStrategyId() {
        UpdateStrategyDto positionUpdate = UpdateStrategyDto.builder()
                .id(samplePosition.getId()).strategyId(sampleStrategy.getId()).build();

        when(positionAccessService.findById(any())).thenReturn(Optional.of(samplePosition));
        when(strategyAccessService.findById(any())).thenReturn(Optional.of(sampleStrategy));
        samplePosition.setStrategy(sampleStrategy);
        when(positionAccessService.save(any())).thenReturn(samplePosition);

        PositionSummaryDto actualPositionSummaryDto = positionService.updateStrategyId(positionUpdate);

        assertNotNull(actualPositionSummaryDto);
        assertEquals(samplePosition.getId(), actualPositionSummaryDto.getId());
        assertEquals(sampleStrategy.getId(), actualPositionSummaryDto.getStrategyId());
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
        newADYENposition.setId(null);

        Position newAMZNposition = getAMZNPosition();
        newAMZNposition.setId(null);
        newAMZNposition.setReportDate(LocalDate.of(2022, 7, 7));

        List<Position> existingPositions = List.of(existingADYENposition, existingADBEposition);
        List<Position> newPositions = List.of(newADYENposition, newAMZNposition);

        // mock
        when(positionAccessService.findAll()).thenReturn(existingPositions);
        when(positionAccessService.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // test
        UpdateReport<Position> result = positionService.updatePositions(newPositions);

        // verify
        assertEquals(1, result.getAdded().size());
        assertEquals(1, result.getDeleted().size());
        assertEquals(1, result.getMerged().size());
        assertEquals(result.getAdded().get(0).getSymbol(), "AMZN");
        assertEquals(result.getDeleted().get(0).getSymbol(), "ADBE");
        assertEquals(LocalDate.of(2022, 7, 7), result.getMerged().get(0).getReportDate());
        assertEquals(sampleStrategy, result.getMerged().get(0).getStrategy());
    }

    @Test
    void updatePositionsEmptyList() {
        UpdateReport<Position> result = positionService.updatePositions(List.of());

        assertEquals(0, result.getAdded().size());
        assertEquals(0, result.getDeleted().size());
        assertEquals(0, result.getMerged().size());
    }
}
