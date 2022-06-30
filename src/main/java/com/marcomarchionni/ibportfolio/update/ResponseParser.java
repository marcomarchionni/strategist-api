package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.update.FlexQueryData;

import java.util.List;


public interface ResponseParser {

    FlexQueryData parse (FlexQueryResponseDto dto);

}
