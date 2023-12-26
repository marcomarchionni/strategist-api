package com.marcomarchionni.ibportfolio.services.parsers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.mappers.FlexStatementMapper;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.mappers.TradeMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    private Predicate<FlexQueryResponseDto.OpenPosition> isValidOpenPosition() {
        return p -> p.getLevelOfDetail().equalsIgnoreCase("SUMMARY");
    }

    private Predicate<FlexQueryResponseDto.ChangeInDividendAccrual> isValidClosedDividend() {
        return cd ->
                cd.getLevelOfDetail().equalsIgnoreCase("DETAIL") && cd.getCode().equalsIgnoreCase("Re") &&
                        cd.getDate().equals(cd.getPayDate());
    }

    @Override
    public FlexStatement getFlexStatement(FlexQueryResponseDto dto) {
        FlexQueryResponseDto.FlexStatement fs = dto.nullSafeGetFlexStatement();
        return flexStatementMapper.toFlexStatement(fs);
    }

    @Override
    public List<Trade> getTrades(FlexQueryResponseDto dto) {
        return dto.nullSafeGetOrders().stream().map(tradeMapper::toTrade).toList();
    }

    @Override
    public List<Position> getPositions(FlexQueryResponseDto dto) {
        return dto.nullSafeGetOpenPositions()
                .stream()
                .filter(isValidOpenPosition())
                .map(positionMapper::toPosition)
                .toList();
    }

    @Override
    public List<Dividend> getClosedDividends(FlexQueryResponseDto dto) {
        return dto.nullSafeGetChangeInDividendAccruals()
                .stream()
                .filter(isValidClosedDividend())
                .map(dividendMapper::toClosedDividend)
                .toList();
    }

    @Override
    public List<Dividend> getOpenDividends(FlexQueryResponseDto dto) {
        return dto.nullSafeGetOpenDividendAccruals()
                .stream()
                .map(dividendMapper::toOpenDividend)
                .toList();
    }

    @Override
    public LocalDate getFlexStatementToDate(FlexQueryResponseDto dto) {
        return dto.getFlexStatements().getFlexStatement().getToDate();
    }
}
