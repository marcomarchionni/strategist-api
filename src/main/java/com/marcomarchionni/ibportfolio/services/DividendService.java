package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface DividendService {

    //    void saveDividends(List<Dividend> dividends);
    UpdateReport<Dividend> addOrSkip(User user, List<Dividend> closedDividends);

    UpdateReport<Dividend> updateDividends(User user, List<Dividend> openDividends, List<Dividend> closedDividends);

    DividendSummaryDto updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendSummaryDto> findByFilter(User user, DividendFindDto dividendCriteria);

}
