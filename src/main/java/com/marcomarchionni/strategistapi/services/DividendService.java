package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.dtos.request.DividendFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummaryDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;

import java.util.List;

public interface DividendService {
    DividendSummaryDto updateStrategyId(UpdateStrategyDto dividendToUpdate);

    List<DividendSummaryDto> findByFilter(DividendFindDto dividendCriteria);

    UpdateReport<Dividend> updateDividends(List<Dividend> dividends);
}
