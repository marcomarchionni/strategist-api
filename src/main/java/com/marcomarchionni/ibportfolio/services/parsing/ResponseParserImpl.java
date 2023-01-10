package com.marcomarchionni.ibportfolio.services.parsing;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapper;
import com.marcomarchionni.ibportfolio.model.mapping.FlexStatementMapper;
import com.marcomarchionni.ibportfolio.model.mapping.PositionMapper;
import com.marcomarchionni.ibportfolio.model.mapping.TradeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;


@Component
public class ResponseParserImpl implements ResponseParser {

    private final FlexStatementMapper flexStatementMapper;
    private final TradeMapper tradeMapper;
    private final PositionMapper positionMapper;
    private final DividendMapper dividendMapper;

    public ResponseParserImpl(FlexStatementMapper flexStatementMapper, TradeMapper tradeMapper, PositionMapper positionMapper, DividendMapper dividendMapper) {
        this.flexStatementMapper = flexStatementMapper;
        this.tradeMapper = tradeMapper;
        this.positionMapper = positionMapper;
        this.dividendMapper = dividendMapper;
    }

    private static Predicate<FlexQueryResponse.OpenPosition> isValidOpenPosition() {
        return p -> p.getLevelOfDetail().equalsIgnoreCase("SUMMARY");
    }

    private Predicate<FlexQueryResponse.ChangeInDividendAccrual> isValidClosedDividend() {
        return cd ->
                cd.getLevelOfDetail().equalsIgnoreCase("SUMMARY") &&
                        cd.getDate().equals(cd.getPayDate());
    }

    @Override
    public FlexStatement getFlexStatement(FlexQueryResponse dto) {
        FlexQueryResponse.FlexStatement fs = dto.getFlexStatement();
        return flexStatementMapper.toFlexStatement(fs);
    }

    @Override
    public List<Trade> getTrades(FlexQueryResponse dto) {
        List<FlexQueryResponse.Order> ordersDto = dto.getOrdersDto();
        return ordersDto.stream().map(tradeMapper::toTrade).toList();
    }

    @Override
    public List<Position> getPositions(FlexQueryResponse dto) {
        List<FlexQueryResponse.OpenPosition> positionsDto = dto.getPositionsDto();
        return positionsDto
                .stream()
                .filter(isValidOpenPosition())
                .map(positionMapper::toPosition)
                .toList();
    }

    @Override
    public List<Dividend> getClosedDividends(FlexQueryResponse dto) {

        List<FlexQueryResponse.ChangeInDividendAccrual> closedDividendsDto = dto.getClosedDividendsDto();
        return closedDividendsDto
                .stream()
                .filter(isValidClosedDividend())
                .map(dividendMapper::toDividend)
                .toList();
    }

    @Override
    public List<Dividend> getOpenDividends(FlexQueryResponse dto) {

        List<FlexQueryResponse.OpenDividendAccrual> openDividendsDto = dto.getOpenDividendsDto();
        return openDividendsDto
                .stream()
                .map(dividendMapper::toDividend)
                .toList();
    }
}
