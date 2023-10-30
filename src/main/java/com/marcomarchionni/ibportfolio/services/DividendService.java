package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface DividendService {

    void saveDividends(List<Dividend> dividends);

    DividendListDto updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendListDto> findByFilter(DividendFindDto dividendCriteria);

    UpdateReport<Dividend> addOrSkip(List<Dividend> closedDividends);

    UpdateReport<Dividend> updateDividends(List<Dividend> openDividends, List<Dividend> closedDividends);
}
