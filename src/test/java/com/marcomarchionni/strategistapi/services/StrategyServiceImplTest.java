package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;
import com.marcomarchionni.strategistapi.mappers.StrategyMapper;
import com.marcomarchionni.strategistapi.mappers.StrategyMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StrategyServiceImplTest {

    @Mock
    StrategyAccessService strategyAccessService;
    @Mock
    PortfolioAccessService portfolioAccessService;
    StrategyMapper strategyMapper;
    StrategyServiceImpl strategyService;
    List<Strategy> userStrategies;
    User user;
    Strategy userStrategy;

    @BeforeEach
    void setup() {
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
        strategyService = new StrategyServiceImpl(strategyAccessService, portfolioAccessService, strategyMapper);

        user = getSampleUser();
        userStrategy = getSampleStrategy();
        userStrategies = getSampleStrategies();
    }

    @Test
    void findByParams() {
        // setup
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();
        when(strategyAccessService.findByParams(strategyFindDto.getName())).thenReturn(userStrategies);

        // execute
        List<StrategySummaryDto> actualStrategies = strategyService.findByFilter(strategyFindDto);

        // verify
        assertNotNull(actualStrategies);
        assertTrue(actualStrategies.stream().allMatch(dto -> dto.getAccountId()
                .equals(user.getAccountId())));
    }

    @Test
    void findByIdSuccess() {
        // setup
        userStrategy.getTrades().add(getSampleTrade());
        when(strategyAccessService.findById(userStrategy.getId())).thenReturn(Optional.of(userStrategy));

        // execute
        StrategyDetailDto strategyDetailDto = strategyService.findById(userStrategy.getId());

        // verify
        assertNotNull(strategyDetailDto);
        assertEquals(userStrategy.getId(), strategyDetailDto.getId());
        assertEquals(userStrategy.getTrades().size(), strategyDetailDto.getTrades().size());
    }

    @Test
    void create() {
        // setup test data
        Portfolio userPortfolio = getSamplePortfolio("MyPortfolio");
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("ZM long")
                .portfolioId(userPortfolio.getId()).build();

        // setup mocks
        when(portfolioAccessService.findById(userPortfolio.getId())).thenReturn(Optional.of(userPortfolio));
        when(strategyAccessService.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetailDto actualStrategy = strategyService.create(strategyCreateDto);

        // verify
        assertNotNull(actualStrategy);
        assertEquals(strategyCreateDto.getName(), actualStrategy.getName());
        assertEquals(userPortfolio.getId(), actualStrategy.getPortfolioId());
    }

    @Test
    void updateName() {
        // setup test data
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(userStrategy.getId()).name("NewName").build();

        // setup mocks
        when(strategyAccessService.findById(userStrategy.getId())).thenReturn(Optional.of(userStrategy));
        when(strategyAccessService.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetailDto renamedStrategy = strategyService.updateName(updateNameDto);

        // verify
        assertNotNull(renamedStrategy);
        assertEquals(userStrategy.getId(), renamedStrategy.getId());
        assertEquals(updateNameDto.getName(), renamedStrategy.getName());
    }

    @Test
    void deleteByIdSuccess() {
        // setup entities
        Long strategyId = userStrategy.getId();

        // setup mocks
        when(strategyAccessService.findById(strategyId)).thenReturn(Optional.of(userStrategy));
        doNothing().when(strategyAccessService).delete(userStrategy);

        // execute
        strategyService.deleteById(strategyId);

        // verify
        verify(strategyAccessService, times(1)).findById(strategyId);
        verify(strategyAccessService, times(1)).delete(userStrategy);
    }
}