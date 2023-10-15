package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);

    Dividend toDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto);

    Dividend toDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto);
}
