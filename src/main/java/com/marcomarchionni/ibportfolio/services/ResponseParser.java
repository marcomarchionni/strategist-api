package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.FlexInfo;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;

import java.util.List;


public interface ResponseParser {

    FlexInfo parseFlexInfo(FlexQueryResponseDto dto);

    List<Trade> parseTrades(FlexQueryResponseDto dto);

    List<Position> parsePositions(FlexQueryResponseDto dto);

    List<Dividend> parseClosedDividends(FlexQueryResponseDto dto);

    List<Dividend> parseOpenDividends(FlexQueryResponseDto dto);
}
