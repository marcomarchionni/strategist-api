package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;
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

        StrategySummaryDto strategySummaryDto = strategyMapper.toStrategySummaryDto(strategy);

        assertNotNull(strategySummaryDto);
        assertEquals(strategy.getId(), strategySummaryDto.getId());
        assertEquals(strategy.getPortfolio().getId(), strategySummaryDto.getPortfolioId());
        assertEquals(strategy.getPortfolio().getName(), strategySummaryDto.getPortfolioName());
    }
}