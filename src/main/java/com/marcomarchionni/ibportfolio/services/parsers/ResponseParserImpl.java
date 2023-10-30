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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                cd.getLevelOfDetail().equalsIgnoreCase("SUMMARY") &&
                        cd.getDate().equals(cd.getPayDate());
    }

    @Override
    public FlexStatement getFlexStatement(FlexQueryResponseDto dto) {
        FlexQueryResponseDto.FlexStatement fs = Optional.ofNullable(dto.getFlexStatements())
                .map(FlexQueryResponseDto.FlexStatements::getFlexStatement)
                .orElse(null);
        return flexStatementMapper.toFlexStatement(fs);
    }

    @Override
    public List<Trade> getTrades(FlexQueryResponseDto dto) {
        List<FlexQueryResponseDto.Order> ordersDto =
                Optional.ofNullable(dto.getFlexStatements())
                        .map(FlexQueryResponseDto.FlexStatements::getFlexStatement)
                        .map(FlexQueryResponseDto.FlexStatement::getTrades)
                        .map(FlexQueryResponseDto.Trades::getOrderList).orElse(Collections.emptyList());
        return ordersDto.stream().map(tradeMapper::toTrade).toList();
    }

    @Override
    public List<Position> getPositions(FlexQueryResponseDto dto) {
        List<FlexQueryResponseDto.OpenPosition> positionsDto = Optional.ofNullable(dto.getFlexStatements())
                .map(FlexQueryResponseDto.FlexStatements::getFlexStatement)
                .map(FlexQueryResponseDto.FlexStatement::getOpenPositions)
                .map(FlexQueryResponseDto.OpenPositions::getOpenPositionList)
                .orElse(Collections.emptyList());

        return positionsDto
                .stream()
                .filter(isValidOpenPosition())
                .map(positionMapper::toPosition)
                .toList();
    }

    @Override
    public List<Dividend> getClosedDividends(FlexQueryResponseDto dto) {

        List<FlexQueryResponseDto.ChangeInDividendAccrual> closedDividendsDto = Optional.ofNullable(dto.getFlexStatements())
                .map(FlexQueryResponseDto.FlexStatements::getFlexStatement)
                .map(FlexQueryResponseDto.FlexStatement::getChangeInDividendAccruals)
                .map(FlexQueryResponseDto.ChangeInDividendAccruals::getChangeInDividendAccrualList)
                .orElse(Collections.emptyList());
        return closedDividendsDto
                .stream()
                .filter(isValidClosedDividend())
                .map(dividendMapper::toClosedDividend)
                .toList();
    }

    @Override
    public List<Dividend> getOpenDividends(FlexQueryResponseDto dto) {

        List<FlexQueryResponseDto.OpenDividendAccrual> openDividendsDto = Optional.ofNullable(dto.getFlexStatements())
                .map(FlexQueryResponseDto.FlexStatements::getFlexStatement)
                .map(FlexQueryResponseDto.FlexStatement::getOpenDividendAccruals)
                .map(FlexQueryResponseDto.OpenDividendAccruals::getOpenDividendAccrualList)
                .orElse(Collections.emptyList());
        return openDividendsDto
                .stream()
                .map(dividendMapper::toOpenDividend)
                .toList();
    }

    @Override
    public LocalDate getFlexStatementToDate(FlexQueryResponseDto dto) {
        return dto.getFlexStatements().getFlexStatement().getToDate();
    }
}
