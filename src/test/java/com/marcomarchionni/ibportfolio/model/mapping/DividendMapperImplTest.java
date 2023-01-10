package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DividendMapperImplTest {

    ModelMapperConfig modelMapperConfig;

    ModelMapper modelMapper;

    DividendMapperImpl dividendMapper;

    @BeforeEach
    void init() {

        modelMapperConfig = new ModelMapperConfig();
        modelMapper = modelMapperConfig.modelMapper();
        dividendMapper = new DividendMapperImpl(modelMapper);
    }

    @Test
    void validateMapping() {
        assertDoesNotThrow(() -> modelMapper.validate());
    }

    @Test
    void toDividendListDto() {
    }

    @Test
    void toClosedDividend() {
        FlexQueryResponse.ChangeInDividendAccrual d = new FlexQueryResponse.ChangeInDividendAccrual();
        d.setCurrency("USD");
        d.setFxRateToBase(BigDecimal.valueOf(0.93292));
        d.setAssetCategory("STK");
        d.setSymbol("CGNX");
        d.setExDate(LocalDate.of(2022, 5, 19));
        d.setPayDate(LocalDate.of(2022, 6, 3));
        d.setDate(LocalDate.of(2022, 6, 3));
        d.setLevelOfDetail("SUMMARY");
        d.setConid(267547L);

        Dividend dividend = dividendMapper.toDividend(d);

        assertNotNull(dividend);
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
        assertEquals(26754720220603L, dividend.getId());
    }

    @Test
    void toOpenDividend() {
        FlexQueryResponse.OpenDividendAccrual d = new FlexQueryResponse.OpenDividendAccrual();
        d.setCurrency("USD");
        d.setFxRateToBase(BigDecimal.valueOf(0.93292));
        d.setAssetCategory("STK");
        d.setSymbol("CGNX");
        d.setExDate(LocalDate.of(2022, 5, 19));
        d.setPayDate(LocalDate.of(2022, 6, 3));
        d.setConid(267547L);

        Dividend dividend = dividendMapper.toDividend(d);

        assertNotNull(dividend);
        assertEquals(LocalDate.of(2022, 5, 19), dividend.getExDate());
        assertEquals("CGNX", dividend.getSymbol());
        assertEquals(26754720220603L, dividend.getId());

    }
}