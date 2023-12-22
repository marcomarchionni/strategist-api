package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.mappers.PositionMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
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

        assertThrows(UnableToDeleteEntitiesException.class,
                () -> positionService.deleteAll(List.of(getAMZNPosition())));
    }

    @Test
    void findWithParameters() {
        when(positionRepository.findByParams(any(), any(), any())).thenReturn(samplePositions);

        List<PositionSummaryDto> positions = positionService.findByFilter(positionFind);

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
        when(positionRepository.findAllByAccountId(user.getAccountId())).thenReturn(existingPositions);
        when(positionRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // test
        UpdateReport<Position> result = positionService.updatePositions(user, newPositions);

        // verify
        assertEquals(1, result.getAdded().size());
        assertEquals(1, result.getDeleted().size());
        assertEquals(1, result.getMerged().size());
        assertEquals(result.getAdded().get(0).getSymbol(), "AMZN");
        assertEquals(result.getDeleted().get(0).getSymbol(), "ADBE");
        assertEquals(LocalDate.of(2022, 7, 7), result.getMerged().get(0).getReportDate());
        assertEquals(sampleStrategy, result.getMerged().get(0).getStrategy());
    }
}
