package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    TradeSummaryDto updateStrategyId(UpdateStrategyDto trade);

    List<TradeSummaryDto> findByFilter(TradeFindDto tradeCriteria);

    UpdateReport<Trade> addOrSkip(List<Trade> trades);
}
