package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.TradeFindDto;

import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    Trade updateStrategyId(UpdateStrategyDto trade);

    List<Trade> findWithCriteria(TradeFindDto tradeCriteria);
}
