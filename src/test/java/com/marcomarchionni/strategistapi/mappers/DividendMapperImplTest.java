package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.marcomarchionni.strategistapi.db.DbTest.getFDXDividend;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
import static org.junit.jupiter.api.Assertions.*;

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
        DividendSummary dto = dividendMapper.toDividendListDto(source);

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
        FlexQueryResponseDto.ChangeInDividendAccrual d = FlexQueryResponseDto.ChangeInDividendAccrual.builder().build();


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
        assertNull(dividend.getId());
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
        assertEquals(BigDecimal.valueOf(0.1), dividend.getGrossAmount());
        assertEquals(Dividend.OpenClosed.CLOSED, dividend.getOpenClosed());
    }

    @Test
    void toOpenDividend() {
        FlexQueryResponseDto.OpenDividendAccrual d = FlexQueryResponseDto.OpenDividendAccrual.builder()
                .currency("USD")
                .fxRateToBase(BigDecimal.valueOf(0.93292))
                .assetCategory("STK")
                .symbol("CGNX")
                .exDate(LocalDate.of(2022, 5, 19))
                .payDate(LocalDate.of(2022, 6, 3))
                .conid(267547L)
                .build();

        Dividend dividend = dividendMapper.toOpenDividend(d);

        assertNotNull(dividend);
        assertNull(dividend.getId());
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());

    }

    @Test
    void mergeFlexProperties() {
        // Setup source (dividend coming from IB)
        Dividend source = getFDXDividend();
        source.setId(null);
        source.setStrategy(null);
        source.setOpenClosed(Dividend.OpenClosed.CLOSED);

        // Setup target (dividend coming from DB)
        Dividend target = getFDXDividend();
        target.setId(1L);
        target.setStrategy(getSampleStrategy());
        target.setOpenClosed(Dividend.OpenClosed.OPEN);

        // Execute merge
        Dividend merged = dividendMapper.mergeFlexProperties(source, target);

        // Assert
        assertNotNull(merged.getId());
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
        assertEquals(target.getStrategy(), merged.getStrategy());
    }
}