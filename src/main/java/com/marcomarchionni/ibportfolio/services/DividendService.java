package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();

    DividendListDto updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendListDto> findByFilter(DividendFindDto dividendCriteria);
}
