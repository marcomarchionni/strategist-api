package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrade;
import static org.junit.jupiter.api.Assertions.*;

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
        System.out.println(trade);

        TradeListDto tradeListDto = tradeMapper.toTradeListDto(trade);

        System.out.println(tradeListDto);
        assertEquals(trade.getId(), tradeListDto.getId());
        assertEquals(trade.getStrategy().getName(), tradeListDto.getStrategyName());
    }
}