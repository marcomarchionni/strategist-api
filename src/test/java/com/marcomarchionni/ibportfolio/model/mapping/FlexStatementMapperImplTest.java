package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.mappers.FlexStatementMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlexStatementMapperImplTest {
    FlexStatementMapperImpl flexStatementMapper;

    @BeforeEach
    void init() {
        flexStatementMapper = new FlexStatementMapperImpl();

    }

    @Test
    void toFlexStatement() {
        FlexQueryResponseDto.FlexStatement flexDto = new FlexQueryResponseDto.FlexStatement();
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