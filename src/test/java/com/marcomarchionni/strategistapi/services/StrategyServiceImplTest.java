package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.request.UpdateName;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
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
        StrategyFind strategyFind = StrategyFind.builder().build();
        when(strategyAccessService.findByParams(strategyFind.getName())).thenReturn(userStrategies);

        // execute
        List<StrategySummary> actualStrategies = strategyService.findByFilter(strategyFind);

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
        StrategyDetail strategyDetail = strategyService.findById(userStrategy.getId());

        // verify
        assertNotNull(strategyDetail);
        assertEquals(userStrategy.getId(), strategyDetail.getId());
        assertEquals(userStrategy.getTrades().size(), strategyDetail.getTrades().size());
    }

    @Test
    void create() {
        // setup test data
        Portfolio userPortfolio = getSamplePortfolio("MyPortfolio");
        StrategyCreate strategyCreate = StrategyCreate.builder().name("ZM long")
                .portfolioId(userPortfolio.getId()).build();

        // setup mocks
        when(portfolioAccessService.findById(userPortfolio.getId())).thenReturn(Optional.of(userPortfolio));
        when(strategyAccessService.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetail actualStrategy = strategyService.create(strategyCreate);

        // verify
        assertNotNull(actualStrategy);
        assertEquals(strategyCreate.getName(), actualStrategy.getName());
        assertEquals(userPortfolio.getId(), actualStrategy.getPortfolioId());
    }

    @Test
    void updateName() {
        // setup test data
        UpdateName updateName = UpdateName.builder().id(userStrategy.getId()).name("NewName").build();

        // setup mocks
        when(strategyAccessService.findById(userStrategy.getId())).thenReturn(Optional.of(userStrategy));
        when(strategyAccessService.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetail renamedStrategy = strategyService.updateName(updateName);

        // verify
        assertNotNull(renamedStrategy);
        assertEquals(userStrategy.getId(), renamedStrategy.getId());
        assertEquals(updateName.getName(), renamedStrategy.getName());
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