package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapper;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StrategyServiceImplTest {

    @Mock
    StrategyRepository strategyRepository;
    @Mock
    PortfolioRepository portfolioRepository;
    StrategyMapper strategyMapper;
    StrategyServiceImpl strategyService;
    List<Strategy> userStrategies;
    User user;
    Strategy userStrategy;

    @BeforeEach
    void setup() {
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
        strategyService = new StrategyServiceImpl(strategyRepository, portfolioRepository, strategyMapper);

        user = getSampleUser();
        userStrategy = getSampleStrategy();
        userStrategies = getSampleStrategies();
    }

    @Test
    void findByParams() {
        // setup
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();
        when(strategyRepository.findByParams(user.getAccountId(), strategyFindDto.getName())).thenReturn(userStrategies);

        // execute
        List<StrategySummaryDto> actualStrategies = strategyService.findByFilter(user, strategyFindDto);

        // verify
        assertNotNull(actualStrategies);
        assertTrue(actualStrategies.stream().allMatch(dto -> dto.getAccountId()
                .equals(user.getAccountId())));
    }

    @Test
    void findByIdSuccess() {
        // setup
        userStrategy.getTrades().add(getSampleTrade());
        when(strategyRepository.findByIdAndAccountId(userStrategy.getId(), user.getAccountId())).thenReturn(Optional.of(userStrategy));

        // execute
        StrategyDetailDto strategyDetailDto = strategyService.findByUserAndId(user, userStrategy.getId());

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
        when(portfolioRepository.findByIdAndAccountId(userPortfolio.getId(), user.getAccountId())).thenReturn(Optional.of(userPortfolio));
        when(strategyRepository.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetailDto actualStrategy = strategyService.create(user, strategyCreateDto);

        // verify
        assertNotNull(actualStrategy);
        assertEquals(strategyCreateDto.getName(), actualStrategy.getName());
        assertEquals(user.getAccountId(), actualStrategy.getAccountId());
        assertEquals(userPortfolio.getId(), actualStrategy.getPortfolioId());
    }

    @Test
    void updateName() {
        // setup test data
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(userStrategy.getId()).name("NewName").build();

        // setup mocks
        when(strategyRepository.findByIdAndAccountId(userStrategy.getId(), user.getAccountId())).thenReturn(Optional.of(userStrategy));
        when(strategyRepository.save(any(Strategy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute
        StrategyDetailDto renamedStrategy = strategyService.updateName(user, updateNameDto);

        // verify
        assertNotNull(renamedStrategy);
        assertEquals(userStrategy.getId(), renamedStrategy.getId());
        assertEquals(updateNameDto.getName(), renamedStrategy.getName());
    }

    @Test
    void deleteByIdSuccess() {
        // setup entities
        Long strategyId = userStrategy.getId();
        String accountId = user.getAccountId();

        // setup mocks
        when(strategyRepository.findByIdAndAccountId(strategyId, accountId)).thenReturn(Optional.of(userStrategy));
        doNothing().when(strategyRepository).deleteById(strategyId);

        // execute
        strategyService.deleteByUserAndId(user, strategyId);

        // verify
        verify(strategyRepository, times(1)).findByIdAndAccountId(strategyId, accountId);
        verify(strategyRepository, times(1)).deleteById(strategyId);
    }
}