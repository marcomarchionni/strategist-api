package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.response.TradeListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrade;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeMapperTest {

    TradeMapperImpl tradeMapper;

    @BeforeEach
    void setup() {
        ModelMapper mapper = new ModelMapper();
        tradeMapper = new TradeMapperImpl(mapper);
    }

    @Test
    void toTradeListDto() {
        Strategy strategy = getSampleStrategy();
        Trade trade = getSampleTrade();
        trade.setStrategy(strategy);

        TradeListDto tradeListDto = tradeMapper.toTradeListDto(trade);

        assertEquals(trade.getId(), tradeListDto.getId());
        assertEquals(trade.getStrategy().getName(), tradeListDto.getStrategyName());
    }
}