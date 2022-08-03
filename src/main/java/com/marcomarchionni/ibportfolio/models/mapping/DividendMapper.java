package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);
}
