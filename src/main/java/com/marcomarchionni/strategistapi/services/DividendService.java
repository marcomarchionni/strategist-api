package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;

import java.util.List;

public interface DividendService {
    DividendSummary updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendSummary> findByFilter(DividendFind dividendCriteria);

    UpdateReport<Dividend> updateDividends(List<Dividend> dividends);
}
