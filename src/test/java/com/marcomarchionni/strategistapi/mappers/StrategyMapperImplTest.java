package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePortfolio;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
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

        StrategySummary strategySummary = strategyMapper.toStrategySummaryDto(strategy);

        assertNotNull(strategySummary);
        assertEquals(strategy.getId(), strategySummary.getId());
        assertEquals(strategy.getPortfolio().getId(), strategySummary.getPortfolioId());
        assertEquals(strategy.getPortfolio().getName(), strategySummary.getPortfolioName());
    }
}