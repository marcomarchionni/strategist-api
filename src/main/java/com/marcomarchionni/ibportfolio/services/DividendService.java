package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);
    void deleteAllOpenDividends();
}
