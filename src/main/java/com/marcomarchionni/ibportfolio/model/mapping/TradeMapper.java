package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.model.dtos.response.TradeListDto;

public interface TradeMapper {

    TradeListDto toTradeListDto(Trade trade);

    Trade toTrade(FlexQueryResponse.Trade tradeDto);

    Trade toTrade(FlexQueryResponse.Order orderDto);
}
