package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSamplePosition;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.*;

class PositionMapperImplTest {

    PositionMapper positionMapper;

    @BeforeEach
    void setUp() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        positionMapper = new PositionMapperImpl(mapper);
    }

    @Test
    void toPositionSummary() {
        Position position = getSamplePosition();
        Strategy strategy = getSampleStrategy();
        position.setStrategy(strategy);

        PositionSummary positionSummary = positionMapper.toPositionSummary(position);

        assertEquals(position.getId(), positionSummary.getId());
        assertEquals(position.getStrategy().getId(), positionSummary.getStrategyId());
        assertEquals(position.getStrategy().getName(), positionSummary.getStrategyName());
    }

    @Test
    void toPositionSummaryNullStrategy() {
        Position position = getSamplePosition();
        position.setStrategy(null);

        PositionSummary positionSummary = positionMapper.toPositionSummary(position);

        assertEquals(position.getId(), positionSummary.getId());
        assertEquals(position.getSymbol(), positionSummary.getSymbol());
        assertNull(positionSummary.getStrategyId());
        assertNull(positionSummary.getStrategyName());
    }

    @Test
    void toPosition() {
        // Set all fields of the dto to avoid null pointer exceptions
        FlexQueryResponseDto.OpenPosition p = FlexQueryResponseDto.OpenPosition.builder()
                .accountId("U1111111")
                .currency("EUR")
                .assetCategory("STK")
                .symbol("ADYEN")
                .description("ADYEN NV")
                .conid(321202935L)
                .reportDate(LocalDate.of(2022, 6, 30))
                .position(BigDecimal.valueOf(1))
                .markPrice(BigDecimal.valueOf(1388))
                .levelOfDetail("SUMMARY")
                .costBasisMoney(BigDecimal.valueOf(1388))
                .costBasisPrice(BigDecimal.valueOf(1388))
                .percentOfNAV(BigDecimal.valueOf(1))
                .fifoPnlUnrealized(BigDecimal.valueOf(0))
                .build();

        Position position = positionMapper.toPosition(p);

        assertNotNull(position);
        assertNull(position.getId());
        assertEquals(p.getMarkPrice(), position.getMarkPrice());
        assertEquals(p.getConid(), position.getConId());
    }

    @Test
    void mergeIbProperties() {
        Position source = getSamplePosition();
        source.setId(null);
        source.setQuantity(BigDecimal.valueOf(3));

        Strategy strategy = getSampleStrategy();
        Position target = getSamplePosition();
        target.setId(1L);
        target.setStrategy(strategy);
        target.setQuantity(BigDecimal.valueOf(2));

        Position merged = positionMapper.mergeFlexProperties(source, target);

        assertEquals(1L, merged.getId());
        assertEquals(source.getConId(), merged.getConId());
        assertEquals(source.getReportDate(), merged.getReportDate());
        assertEquals(source.getSymbol(), merged.getSymbol());
        assertEquals(source.getDescription(), merged.getDescription());
        assertEquals(source.getAssetCategory(), merged.getAssetCategory());
        assertEquals(source.getPutCall(), merged.getPutCall());
        assertEquals(source.getStrike(), merged.getStrike());
        assertEquals(source.getExpiry(), merged.getExpiry());
        assertEquals(source.getQuantity(), merged.getQuantity());
        assertEquals(source.getCostBasisPrice(), merged.getCostBasisPrice());
        assertEquals(source.getCostBasisMoney(), merged.getCostBasisMoney());
        assertEquals(source.getMarkPrice(), merged.getMarkPrice());
        assertEquals(source.getMultiplier(), merged.getMultiplier());
        assertEquals(source.getPositionValue(), merged.getPositionValue());
        assertEquals(source.getFifoPnlUnrealized(), merged.getFifoPnlUnrealized());
        assertEquals(target.getStrategy(), merged.getStrategy());
    }
}