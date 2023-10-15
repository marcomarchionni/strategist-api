package com.marcomarchionni.ibportfolio.services.parsing;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDtoOld;

import java.util.List;


public interface OldResponseParser {

    FlexStatement parseFlexStatement(FlexQueryResponseDtoOld dto);

    List<Trade> parseTrades(FlexQueryResponseDtoOld dto);

    List<Position> parsePositions(FlexQueryResponseDtoOld dto);

    List<Dividend> parseClosedDividends(FlexQueryResponseDtoOld dto);

    List<Dividend> parseOpenDividends(FlexQueryResponseDtoOld dto);
}
