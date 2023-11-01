package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.marcomarchionni.ibportfolio.db.DbTest.getFDXDividend;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DividendMapperImplTest {
    DividendMapperImpl dividendMapper;

    @BeforeEach
    void init() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        dividendMapper = new DividendMapperImpl(mapper);
    }

    @Test
    void toDividendListDto() {
        // Setup source
        Dividend source = getFDXDividend();
        Strategy strategy = getSampleStrategy();
        source.setStrategy(strategy);

        // Map to DTO
        DividendSummaryDto dto = dividendMapper.toDividendListDto(source);

        // Assert
        assertNotNull(dto);
        assertEquals(source.getId(), dto.getId());
        assertEquals(source.getConId(), dto.getConId());
        assertEquals(source.getSymbol(), dto.getSymbol());
        assertEquals(source.getDescription(), dto.getDescription());
        assertEquals(source.getExDate(), dto.getExDate());
        assertEquals(source.getPayDate(), dto.getPayDate());
        assertEquals(source.getGrossRate(), dto.getGrossRate());
        assertEquals(source.getQuantity(), dto.getQuantity());
        assertEquals(source.getGrossAmount(), dto.getGrossAmount());
        assertEquals(source.getTax(), dto.getTax());
        assertEquals(source.getNetAmount(), dto.getNetAmount());
        assertEquals(source.getOpenClosed().name(), dto.getOpenClosed());
        assertEquals(strategy.getId(), dto.getStrategyId());
        assertEquals(strategy.getName(), dto.getStrategyName());
    }

    @Test
    void toClosedDividend() {
        FlexQueryResponseDto.ChangeInDividendAccrual d = new FlexQueryResponseDto.ChangeInDividendAccrual();
        d.setCurrency("USD");
        d.setFxRateToBase(BigDecimal.valueOf(0.93292));
        d.setAssetCategory("STK");
        d.setSymbol("CGNX");
        d.setExDate(LocalDate.of(2022, 5, 19));
        d.setPayDate(LocalDate.of(2022, 6, 3));
        d.setDate(LocalDate.of(2022, 6, 3));
        d.setGrossAmount(BigDecimal.valueOf(-0.1));
        d.setLevelOfDetail("SUMMARY");
        d.setConid(267547L);

        Dividend dividend = dividendMapper.toClosedDividend(d);

        assertNotNull(dividend);
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
        assertEquals(BigDecimal.valueOf(0.1), dividend.getGrossAmount());
        assertEquals(26754720220603L, dividend.getId());
        assertEquals(Dividend.OpenClosed.CLOSED, dividend.getOpenClosed());
    }

    @Test
    void toOpenDividend() {
        FlexQueryResponseDto.OpenDividendAccrual d = new FlexQueryResponseDto.OpenDividendAccrual();
        d.setCurrency("USD");
        d.setFxRateToBase(BigDecimal.valueOf(0.93292));
        d.setAssetCategory("STK");
        d.setSymbol("CGNX");
        d.setExDate(LocalDate.of(2022, 5, 19));
        d.setPayDate(LocalDate.of(2022, 6, 3));
        d.setConid(267547L);

        Dividend dividend = dividendMapper.toOpenDividend(d);

        assertNotNull(dividend);
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
        assertEquals(26754720220603L, dividend.getId());

    }

    @Test
    void mergeIbProperties() {
        Dividend source = getFDXDividend();
        Strategy strategy = getSampleStrategy();
        Dividend target = new Dividend();
        target.setStrategy(strategy);

        Dividend merged = dividendMapper.mergeIbProperties(source, target);

        assertEquals(source.getId(), merged.getId());
        assertEquals(source.getConId(), merged.getConId());
        assertEquals(source.getSymbol(), merged.getSymbol());
        assertEquals(source.getDescription(), merged.getDescription());
        assertEquals(source.getExDate(), merged.getExDate());
        assertEquals(source.getPayDate(), merged.getPayDate());
        assertEquals(source.getGrossRate(), merged.getGrossRate());
        assertEquals(source.getQuantity(), merged.getQuantity());
        assertEquals(source.getGrossAmount(), merged.getGrossAmount());
        assertEquals(source.getTax(), merged.getTax());
        assertEquals(source.getNetAmount(), merged.getNetAmount());
        assertEquals(source.getOpenClosed(), merged.getOpenClosed());
        assertEquals(strategy, merged.getStrategy());
    }
}