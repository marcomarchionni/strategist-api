package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.DividendListDto;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();

    DividendListDto updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendListDto> findByParams(DividendFindDto dividendCriteria);
}
