package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlexStatementMapperImplTest {

    ModelMapperConfig modelMapperConfig;
    ModelMapper modelMapper;
    FlexStatementMapperImpl flexStatementMapper;

    @BeforeEach
    void init() {
        modelMapperConfig = new ModelMapperConfig();
        modelMapper = modelMapperConfig.modelMapper();
        flexStatementMapper = new FlexStatementMapperImpl(modelMapper);

    }

    @Test
    void toFlexStatement() {
        FlexQueryResponse.FlexStatement flexDto = new FlexQueryResponse.FlexStatement();
        flexDto.setAccountId("U7169936");
        flexDto.setFromDate(LocalDate.of(2022, 6, 1));
        flexDto.setToDate(LocalDate.of(2022, 6, 30));
        flexDto.setWhenGenerated(LocalDateTime.of(2022, 12, 28, 12, 48, 35));

        assertDoesNotThrow(() -> {
            flexStatementMapper.toFlexStatement(flexDto);
        });
        FlexStatement flexStatement = flexStatementMapper.toFlexStatement(flexDto);
        assertEquals(flexDto.getAccountId(), flexStatement.getAccountId());
    }
}