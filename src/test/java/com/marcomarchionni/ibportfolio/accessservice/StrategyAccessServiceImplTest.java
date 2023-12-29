package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategies;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

        strategyAccessService = new StrategyAccessServiceImpl(userService, strategyRepository);

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
    void save() {
        when(strategyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        expectedStrategy.setAccountId(null);
        var savedStrategy = strategyAccessService.save(expectedStrategy);

        assertEquals(expectedStrategies.get(0), savedStrategy);
        assertEquals("U1111111", savedStrategy.getAccountId());
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