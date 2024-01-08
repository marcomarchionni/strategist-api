package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.strategistapi.repositories.PositionRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdEntityValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePosition;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePositions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionAccessServiceImplTest {
    @Mock
    UserService userService;
    @Mock
    PositionRepository positionRepository;
    PositionAccessService positionAccessService;

    Position expectedPosition;

    List<Position> expectedPositions;

    @BeforeEach
    void setup() {
        expectedPosition = getSamplePosition();
        expectedPositions = getSamplePositions();
        var accountIdValidator = new AccountIdEntityValidatorImpl<Position>();
        positionAccessService = new PositionAccessServiceImpl(positionRepository, userService, accountIdValidator);

        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void deleteAll() {
        assertDoesNotThrow(() -> positionAccessService.deleteAll(expectedPositions));
    }

    @Test
    void deleteAllException() {
        expectedPositions.get(0).setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> positionAccessService.deleteAll(expectedPositions));
    }

    @Test
    void saveAll() {
        when(positionRepository.saveAll(expectedPositions)).thenReturn(expectedPositions);
        var savedPositions = positionAccessService.saveAll(expectedPositions);
        assertEquals(expectedPositions, savedPositions);
    }

    @Test
    void saveAllEx() {
        expectedPositions.get(0).setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> positionAccessService.saveAll(expectedPositions));
    }

    @Test
    void findByParams() {
        when(positionRepository.findByParams("U1111111", true, null, null)).thenReturn(expectedPositions);
        var positions = positionAccessService.findByParams(true, null, null);
        assertEquals(expectedPositions, positions);
    }

    @Test
    void findAll() {
        when(positionRepository.findAllByAccountId("U1111111")).thenReturn(expectedPositions);
        var positions = positionAccessService.findAll();
        assertEquals(expectedPositions, positions);
    }

    @Test
    void findBySymbol() {
        when(positionRepository.findByAccountIdAndSymbol("U1111111", "AAPL")).thenReturn(
                Optional.ofNullable(expectedPosition));
        var position = positionAccessService.findBySymbol("AAPL");
        assertEquals(expectedPosition, position.get());
    }

    @Test
    void findById() {
        when(positionRepository.findByIdAndAccountId(1L, "U1111111")).thenReturn(
                Optional.ofNullable(expectedPosition));
        var position = positionAccessService.findById(1L);
        assertEquals(expectedPosition, position.get());
    }

    @Test
    void save() {
        when(positionRepository.save(expectedPosition)).thenReturn(expectedPosition);
        var savedPosition = positionAccessService.save(expectedPosition);
        assertEquals(expectedPosition, savedPosition);
        assertEquals("U1111111", savedPosition.getAccountId());
    }

    @Test
    void saveEx() {
        expectedPosition.setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> positionAccessService.save(expectedPosition));
    }
}