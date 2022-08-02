package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.models.domain.Position;
import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.update.flexDtos.FlexQueryResponseDto;

import java.util.List;


public interface ResponseParser {

    FlexInfo parseFlexInfo(FlexQueryResponseDto dto);

    List<Trade> parseTrades(FlexQueryResponseDto dto);

    List<Position> parsePositions(FlexQueryResponseDto dto);

    List<Dividend> parseClosedDividends(FlexQueryResponseDto dto);

    List<Dividend> parseOpenDividends(FlexQueryResponseDto dto);
}
