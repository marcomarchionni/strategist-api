package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PositionMapperImplTest {

    ModelMapper mapper;

    @BeforeEach
    void setUp() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        mapper = modelMapperConfig.modelMapper();
    }

    @Test
    void toPositionListDto() {
    }

    @Test
    void toPosition() {

        FlexQueryResponse.OpenPosition p = new FlexQueryResponse.OpenPosition();
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

        PositionMapper positionMapper = new PositionMapperImpl(mapper);
        Position position = positionMapper.toPosition(p);
        assertNotNull(position);
        assertEquals(p.getMarkPrice(), p.getMarkPrice());
        assertEquals(p.getConid(), position.getId());
        assertEquals(p.getConid(), position.getConId());
    }

    @Test
    void calculateDividendId() {

        LocalDate payDate = LocalDate.of(2022, 6, 30);
        long conid = 267547L;

        Long payDateLong = Long.parseLong(payDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Long bigConId = (long) (conid * 10E7);
        Long id = bigConId + payDateLong;

        assertEquals(26754720220630L, id);


    }
}