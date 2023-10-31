package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrades;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PortfolioMapperImplTest {

    PortfolioMapper portfolioMapper;

    @BeforeEach
    void setup() {
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
    }

    @Test
    void toEntity() {
    }

    @Test
    void toPortfolioListDto() {
    }

    @Test
    void toPortfolioDetailDto() {
        List<Trade> trades = getSampleTrades();
        Strategy strategy = Strategy.builder().id(1L).name("EBAYlong").trades(trades).build();
        Portfolio portfolio = Portfolio.builder().id(1L).name("Saver").strategies(List.of(strategy)).build();

        PortfolioDetailDto portfolioDto = portfolioMapper.toPortfolioDetailDto(portfolio);

        assertNotNull(portfolioDto);
    }
}