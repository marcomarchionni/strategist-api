package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        System.out.println(strategy);
        assertEquals(strategyCreateDto.getName(), strategy.getName());
        assertEquals(portfolio.getId(), strategy.getPortfolio().getId());
    }
}
