package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.DividendCriteriaDto;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();

    Dividend updateStrategyId(Dividend dividendToUpdate);

    List<Dividend> findWithCriteria(DividendCriteriaDto dividendCriteria);
}
