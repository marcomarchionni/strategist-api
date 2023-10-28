package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeListDto;

import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    TradeListDto updateStrategyId(UpdateStrategyDto trade);

    List<TradeListDto> findByFilter(TradeFindDto tradeCriteria);

    List<Trade> saveOrIgnore(List<Trade> trades);
}
