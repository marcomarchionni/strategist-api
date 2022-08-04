package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.TradeListDto;

import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    TradeListDto updateStrategyId(UpdateStrategyDto trade);

    List<TradeListDto> findByFilter(TradeFindDto tradeCriteria);
}
