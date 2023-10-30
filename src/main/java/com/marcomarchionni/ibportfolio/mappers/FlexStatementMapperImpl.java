package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FlexStatementMapperImpl implements FlexStatementMapper {

    @Override
    public FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto) {
        FlexStatement f = new FlexStatement();
        f.setAccountId(flexStatementDto.getAccountId());
        f.setPeriod(flexStatementDto.getPeriod());
        f.setFromDate(flexStatementDto.getFromDate());
        f.setToDate(flexStatementDto.getToDate());
        f.setWhenGenerated(flexStatementDto.getWhenGenerated());
        f.setId(getId(flexStatementDto.getWhenGenerated()));
        return f;
    }

    private Long getId(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = dateTime.format(formatter);
        return Long.parseLong(formattedDateTime);
    }
}
