package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.util.List;

public interface TradeService {

    List<Trade> saveAll(User user, List<Trade> trades);

    TradeSummaryDto updateStrategyId(User user, UpdateStrategyDto trade);

    List<TradeSummaryDto> findByFilter(User user, TradeFindDto tradeCriteria);

    UpdateReport<Trade> addOrSkip(User user, List<Trade> trades);
}
