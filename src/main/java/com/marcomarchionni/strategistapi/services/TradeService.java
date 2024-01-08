package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.request.TradeFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummaryDto;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;

import java.util.List;

public interface TradeService {

    List<Trade> saveAll(List<Trade> trades);

    TradeSummaryDto updateStrategyId(UpdateStrategyDto trade);

    List<TradeSummaryDto> findByFilter(TradeFindDto tradeCriteria);

    UpdateReport<Trade> updateTrades(List<Trade> trades);
}
