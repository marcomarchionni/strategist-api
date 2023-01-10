package com.marcomarchionni.ibportfolio.services.parsing;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;

import java.util.List;

public interface ResponseParser {
    FlexStatement getFlexStatement(FlexQueryResponse dto);

    List<Trade> getTrades(FlexQueryResponse dto);

    List<Position> getPositions(FlexQueryResponse dto);

    List<Dividend> getClosedDividends(FlexQueryResponse dto);

    List<Dividend> getOpenDividends(FlexQueryResponse dto);
}
