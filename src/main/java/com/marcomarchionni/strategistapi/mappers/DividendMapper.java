package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;

public interface DividendMapper {

    DividendSummary toDividendListDto(Dividend dividend);

    Dividend mergeFlexProperties(Dividend source, Dividend target);

    Dividend toClosedDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto);

    Dividend toOpenDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto);
}
