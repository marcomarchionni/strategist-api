package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSamplePosition;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
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
    void toPositionListDto() {
    }

    @Test
    void toPosition() {
        // Set all fields of the dto to avoid null pointer exceptions
        FlexQueryResponseDto.OpenPosition p = new FlexQueryResponseDto.OpenPosition();
        p.setAccountId("U7169936");
        p.setCurrency("EUR");
        p.setAssetCategory("STK");
        p.setSymbol("ADYEN");
        p.setDescription("ADYEN NV");
        p.setConid(321202935L);
        p.setReportDate(LocalDate.of(2022, 6, 30));
        p.setPosition(BigDecimal.valueOf(1));
        p.setMarkPrice(BigDecimal.valueOf(1388));
        p.setLevelOfDetail("SUMMARY");
        p.setCostBasisMoney(BigDecimal.valueOf(1388));
        p.setCostBasisPrice(BigDecimal.valueOf(1388));
        p.setPercentOfNAV(BigDecimal.valueOf(1));
        p.setFifoPnlUnrealized(BigDecimal.valueOf(0));

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