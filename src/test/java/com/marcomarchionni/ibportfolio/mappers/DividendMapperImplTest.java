package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DividendMapperImplTest {
    DividendMapperImpl dividendMapper;

    @BeforeEach
    void init() {
        dividendMapper = new DividendMapperImpl();
    }

    @Test
    void toDividendListDto() {
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
        d.setLevelOfDetail("SUMMARY");
        d.setConid(267547L);

        Dividend dividend = dividendMapper.toClosedDividend(d);

        assertNotNull(dividend);
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
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
}