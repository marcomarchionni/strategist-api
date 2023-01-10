package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);

    Dividend toDividend(FlexQueryResponse.ChangeInDividendAccrual closedDividendDto);

    Dividend toDividend(FlexQueryResponse.OpenDividendAccrual openDividendDto);
}
