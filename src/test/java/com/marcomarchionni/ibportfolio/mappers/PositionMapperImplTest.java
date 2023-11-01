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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals(p.getMarkPrice(), position.getMarkPrice());
        assertEquals(p.getConid(), position.getId());
        assertEquals(p.getConid(), position.getConId());
    }

    @Test
    void mergeIbProperties() {
        Position source = getSamplePosition();
        Strategy strategy = getSampleStrategy();
        Position target = new Position();
        target.setStrategy(strategy);

        Position destination = positionMapper.mergeIbProperties(source, target);
        assertEquals(source.getId(), destination.getId());
        assertEquals(source.getConId(), destination.getConId());
        assertEquals(source.getReportDate(), destination.getReportDate());
        assertEquals(source.getSymbol(), destination.getSymbol());
        assertEquals(source.getDescription(), destination.getDescription());
        assertEquals(source.getAssetCategory(), destination.getAssetCategory());
        assertEquals(source.getPutCall(), destination.getPutCall());
        assertEquals(source.getStrike(), destination.getStrike());
        assertEquals(source.getExpiry(), destination.getExpiry());
        assertEquals(source.getQuantity(), destination.getQuantity());
        assertEquals(source.getCostBasisPrice(), destination.getCostBasisPrice());
        assertEquals(source.getCostBasisMoney(), destination.getCostBasisMoney());
        assertEquals(source.getMarkPrice(), destination.getMarkPrice());
        assertEquals(source.getMultiplier(), destination.getMultiplier());
        assertEquals(source.getPositionValue(), destination.getPositionValue());
        assertEquals(source.getFifoPnlUnrealized(), destination.getFifoPnlUnrealized());
        assertEquals(strategy, destination.getStrategy());
    }
}