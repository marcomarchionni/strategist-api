package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;

import java.util.List;


public interface ResponseParser {

    List<Trade> parseTrades (FlexQueryResponseDto dto);
    List<Position> parsePositions (FlexQueryResponseDto dto);
    List<Dividend> parseDividends (FlexQueryResponseDto dto);

}
