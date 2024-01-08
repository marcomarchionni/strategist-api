package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StrategyMapperTest {

    StrategyMapper strategyMapper;

    @BeforeEach
    void setup() {
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
    }
    @Test
    void toEntitySuccess() {
        StrategyCreateDto strategyCreateDto = StrategyCreateDto.builder().name("ZM long").portfolioId(2L).build();
        Portfolio portfolio = Portfolio.builder().name("Saver").id(2L).build();

        Strategy strategy = strategyMapper.toEntity(strategyCreateDto);

        assertNotNull(strategy);
        assertEquals(strategyCreateDto.getName(), strategy.getName());
        assertEquals(portfolio.getId(), strategy.getPortfolio().getId());
        assertNull(strategy.getId());
    }
}
