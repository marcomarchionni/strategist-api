package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TradeMapperImpl implements TradeMapper {

    ModelMapper modelMapper;

    public TradeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TradeSummary toTradeSummary(Trade trade) {
        return modelMapper.map(trade, TradeSummary.class);
    }

    @Override
    public Trade toTrade(FlexQueryResponseDto.Order orderDto) {
        return modelMapper.map(orderDto, Trade.class);
    }
}
