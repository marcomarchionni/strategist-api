package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;

public interface DividendMapper {

    DividendListDto toDividendListDto(Dividend dividend);
}
