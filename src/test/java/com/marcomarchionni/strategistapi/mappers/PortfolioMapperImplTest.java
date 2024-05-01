package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.marcomarchionni.strategistapi.config.ModelMapperConfig.configureModelMapper;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleTrades;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PortfolioMapperImplTest {

    PortfolioMapper portfolioMapper;

    @BeforeEach
    void setup() {
        portfolioMapper = new PortfolioMapperImpl(configureModelMapper());
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

        PortfolioDetail portfolioDto = portfolioMapper.toPortfolioDetailDto(portfolio);

        assertNotNull(portfolioDto);
    }

    @Test
    void mergePortfolioSaveToPortfolio() {
        PortfolioSave portfolioSave = PortfolioSave.builder().name("Saver").createdAt(LocalDate.now())
                .description("description").build();
        Portfolio portfolio = Portfolio.builder().id(1L).accountId("U1111111").build();

        portfolioMapper.mergePortfolioSaveToPortfolio(portfolioSave, portfolio);

        assertNotNull(portfolio);
        assertEquals(portfolioSave.getName(), portfolio.getName());
        assertEquals(portfolioSave.getCreatedAt(), portfolio.getCreatedAt());
        assertEquals(portfolioSave.getDescription(), portfolio.getDescription());
        assertEquals("U1111111", portfolio.getAccountId(), "accountId should not be updated");
        assertEquals(1L, portfolio.getId(), "id should not be updated");
    }
}