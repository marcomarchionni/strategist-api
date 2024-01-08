package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.strategistapi.repositories.StrategyRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdEntityValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategies;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StrategyAccessServiceImplTest {

    @Mock
    StrategyRepository strategyRepository;
    @Mock
    UserService userService;
    StrategyAccessService strategyAccessService;
    List<Strategy> expectedStrategies;
    Strategy expectedStrategy;


    @BeforeEach
    void setUp() {
        expectedStrategies = getSampleStrategies();
        expectedStrategy = getSampleStrategy();
        var accountIdValidator = new AccountIdEntityValidatorImpl<Strategy>();
        strategyAccessService = new StrategyAccessServiceImpl(userService, strategyRepository, accountIdValidator);

        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void findByParams() {
        when(strategyRepository.findByParams("U1111111", "AAPL long")).thenReturn(expectedStrategies);
        var strategies = strategyAccessService.findByParams("AAPL long");

        assertEquals(expectedStrategies, strategies);
    }

    @Test
    void findById() {
        when(strategyRepository.findByIdAndAccountId(1L, "U1111111")).thenReturn(Optional.ofNullable(expectedStrategy));
        var strategy = strategyAccessService.findById(1L);

        assertEquals(expectedStrategy, strategy.get());
    }

    @Test
    void saveEx() {
        expectedStrategy.setAccountId(null);
        assertThrows(InvalidUserDataException.class, () -> strategyAccessService.save(expectedStrategy));
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> strategyAccessService.delete(expectedStrategy));
    }

    @Test
    void deleteException() {
        expectedStrategy.setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> strategyAccessService.delete(expectedStrategy));
    }
}