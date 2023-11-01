package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);

    Dividend mergeIbProperties(Dividend source, Dividend target);

    Dividend toClosedDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto);

    Dividend toOpenDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto);
}
