package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;

import java.time.LocalDate;
import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();

    List<Dividend> findWithParameters(LocalDate startDate, LocalDate endDate, LocalDate fromPayDate, LocalDate toPayDate, Boolean tagged, String symbol);

    Dividend updateStrategyId(Dividend dividendToUpdate);
}
