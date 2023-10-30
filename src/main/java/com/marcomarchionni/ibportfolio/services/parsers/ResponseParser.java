package com.marcomarchionni.ibportfolio.services.parsers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ResponseParser {
    FlexStatement getFlexStatement(FlexQueryResponseDto dto);

    List<Trade> getTrades(FlexQueryResponseDto dto);

    List<Position> getPositions(FlexQueryResponseDto dto);

    List<Dividend> getClosedDividends(FlexQueryResponseDto dto);

    List<Dividend> getOpenDividends(FlexQueryResponseDto dto);

    LocalDate getFlexStatementToDate(FlexQueryResponseDto dto);
}
