package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyListDto;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapper;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePortfolio;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StrategyMapperImplTest {

    StrategyMapper strategyMapper;

    @BeforeEach
    void setup() {
        strategyMapper = new StrategyMapperImpl(new ModelMapper());
    }

    @Test
    void toStrategyListDto() {
        Portfolio portfolio = getSamplePortfolio("Saver");
        Strategy strategy = getSampleStrategy();
        strategy.setPortfolio(portfolio);

        StrategyListDto strategyListDto = strategyMapper.toStrategyListDto(strategy);

        assertNotNull(strategyListDto);
        assertEquals(strategy.getId(), strategyListDto.getId());
        assertEquals(strategy.getPortfolio().getId(), strategyListDto.getPortfolioId());
        assertEquals(strategy.getPortfolio().getName(), strategyListDto.getPortfolioName());
    }
}