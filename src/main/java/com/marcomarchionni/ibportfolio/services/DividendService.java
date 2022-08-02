package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();

    Dividend updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<Dividend> findByParams(DividendFindDto dividendCriteria);
}
