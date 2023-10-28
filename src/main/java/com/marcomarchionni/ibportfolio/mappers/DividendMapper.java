package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);

    Dividend toDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto);

    Dividend toDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto);
}
