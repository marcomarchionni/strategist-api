package com.marcomarchionni.ibportfolio.services.parsing;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;

import java.util.List;

public interface ResponseParser {
    FlexStatement getFlexStatement(FlexQueryResponseDto dto);

    List<Trade> getTrades(FlexQueryResponseDto dto);

    List<Position> getPositions(FlexQueryResponseDto dto);

    List<Dividend> getClosedDividends(FlexQueryResponseDto dto);

    List<Dividend> getOpenDividends(FlexQueryResponseDto dto);
}
