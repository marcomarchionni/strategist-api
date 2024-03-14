package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;

import java.util.List;

public interface DividendService {
    DividendSummary updateStrategyId(StrategyAssign dividendToUpdate);

    List<DividendSummary> findByFilter(DividendFind dividendCriteria);

    UpdateReport<Dividend> updateDividends(List<Dividend> dividends);
}
