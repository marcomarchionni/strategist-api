package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;

public interface TradeMapper {

    TradeListDto toTradeListDto(Trade trade);
}
