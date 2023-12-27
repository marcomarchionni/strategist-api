package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlexStatementMapperImplTest {
    FlexStatementMapperImpl flexStatementMapper;

    @BeforeEach
    void init() {
        ModelMapperConfig mapperConfig = new ModelMapperConfig();
        ModelMapper mapper = mapperConfig.modelMapper();
        flexStatementMapper = new FlexStatementMapperImpl(mapper);

    }

    @Test
    void toFlexStatement() {
        FlexQueryResponseDto.FlexStatement flexDto = FlexQueryResponseDto.FlexStatement.builder()
                .accountId("U7169936")
                .fromDate(LocalDate.of(2022, 6, 1))
                .toDate(LocalDate.of(2022, 6, 30))
                .whenGenerated(LocalDateTime.of(2022, 12, 28, 12, 48, 35))
                .build();

        assertDoesNotThrow(() -> {
            flexStatementMapper.toFlexStatement(flexDto);
        });

        FlexStatement flexStatement = flexStatementMapper.toFlexStatement(flexDto);

        assertEquals(flexDto.getAccountId(), flexStatement.getAccountId());
        assertEquals(flexDto.getFromDate(), flexStatement.getFromDate());
        assertEquals(flexDto.getToDate(), flexStatement.getToDate());
        assertNull(flexStatement.getId());
    }
}