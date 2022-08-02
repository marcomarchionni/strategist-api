package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;
import com.marcomarchionni.ibportfolio.models.mapping.StrategyMapper;
import com.marcomarchionni.ibportfolio.models.mapping.StrategyMapperImpl;
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

    final List<Strategy> strategies = getSampleStrategies();
    final Strategy strategy = getSampleStrategy();

    @BeforeEach
    void setup() {
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
        strategyService = new StrategyServiceImpl(strategyRepository, portfolioRepository, strategyMapper);
    }

    @Test
    void findByParams() {
        StrategyFindDto strategyFindDto = StrategyFindDto.builder().build();
        when(strategyRepository.findByParams(any())).thenReturn(strategies);

        List<StrategyListDto> actualStrategies = strategyService.findByParams(strategyFindDto);

        assertNotNull(actualStrategies);
    }

    @Test
    void findByIdSuccess() {
        Strategy strategy1 = getSampleStrategy();
        strategy1.getTrades().add(getSampleTrade());

        when(strategyRepository.findById(any())).thenReturn(Optional.of(strategy1));

        StrategyDetailDto strategyDetailDto = strategyService.findById(1L);

        assertNotNull(strategyDetailDto);
        assertEquals(strategy1.getId(), strategyDetailDto.getId());
        assertEquals(strategy1.getTrades().size(), strategyDetailDto.getTrades().size());
    }

    @Test
    void create() {
        Portfolio portfolio = Portfolio.builder().id(1L).name("MyPortfolio").build();
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("ZM long").portfolioId(1L).build();
        Strategy createdStrategy = Strategy.builder().name(strategyCreateDto.getName()).portfolio(portfolio).build();
        Strategy expectedStrategy = Strategy.builder().id(1L).name(strategyCreateDto.getName()).portfolio(portfolio).build();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.ofNullable(portfolio));
        when(strategyRepository.save(createdStrategy)).thenReturn(expectedStrategy);

        Strategy actualStrategy = strategyService.create(strategyCreateDto);

        assertEquals(expectedStrategy.getName(), actualStrategy.getName());
    }

    @Test
    void updateName() {
        UpdateNameDto updateNameDto = UpdateNameDto.builder().id(1L).name("NewName").build();
        Strategy expectedStrategy = Strategy.builder().id(strategy.getId()).name(updateNameDto.getName()).build();

        when(strategyRepository.findById(updateNameDto.getId())).thenReturn(Optional.of(strategy));
        when(strategyRepository.save(expectedStrategy)).thenReturn(expectedStrategy);

        Strategy actualStrategy = strategyService.updateName(updateNameDto);

        assertEquals(updateNameDto.getName(), actualStrategy.getName());
    }

    @Test
    void deleteByIdSuccess() {
        when(strategyRepository.findById(1L)).thenReturn(Optional.of(strategy));
        doNothing().when(strategyRepository).deleteById(1L);

        strategyService.deleteById(1L);

        verify(strategyRepository).deleteById(1L);
    }
}