package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
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

        StrategySummaryDto strategySummaryDto = strategyMapper.toStrategyListDto(strategy);

        assertNotNull(strategySummaryDto);
        assertEquals(strategy.getId(), strategySummaryDto.getId());
        assertEquals(strategy.getPortfolio().getId(), strategySummaryDto.getPortfolioId());
        assertEquals(strategy.getPortfolio().getName(), strategySummaryDto.getPortfolioName());
    }
}