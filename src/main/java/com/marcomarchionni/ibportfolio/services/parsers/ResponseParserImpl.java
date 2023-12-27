package com.marcomarchionni.ibportfolio.services.parsers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateDto;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.mappers.FlexStatementMapper;
import com.marcomarchionni.ibportfolio.mappers.PositionMapper;
import com.marcomarchionni.ibportfolio.mappers.TradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ResponseParserImpl implements ResponseParser {
    private final FlexStatementMapper flexStatementMapper;
    private final TradeMapper tradeMapper;
    private final PositionMapper positionMapper;
    private final DividendMapper dividendMapper;

    private Predicate<FlexQueryResponseDto.OpenPosition> isValidOpenPosition() {
        return p -> p.getLevelOfDetail().equalsIgnoreCase("SUMMARY");
    }

    private Predicate<FlexQueryResponseDto.ChangeInDividendAccrual> isValidClosedDividend() {
        return cd ->
                cd.getLevelOfDetail().equalsIgnoreCase("DETAIL") && cd.getCode().equalsIgnoreCase("Re") &&
                        cd.getDate().equals(cd.getPayDate());
    }

    private FlexStatement getFlexStatement(FlexQueryResponseDto dto) {
        FlexQueryResponseDto.FlexStatement fs = dto.nullSafeGetFlexStatement();
        return flexStatementMapper.toFlexStatement(fs);
    }

    private List<Trade> getTrades(FlexQueryResponseDto dto) {
        return dto.nullSafeGetOrders().stream().map(tradeMapper::toTrade).toList();
    }

    private List<Position> getPositions(FlexQueryResponseDto dto) {
        return dto.nullSafeGetOpenPositions()
                .stream()
                .filter(isValidOpenPosition())
                .map(positionMapper::toPosition)
                .toList();
    }

    private List<Dividend> getDividends(FlexQueryResponseDto dto) {
        var closedDividendsStream = dto.nullSafeGetChangeInDividendAccruals()
                .stream()
                .filter(isValidClosedDividend())
                .map(dividendMapper::toClosedDividend);

        var openDividendsStream = dto.nullSafeGetOpenDividendAccruals()
                .stream()
                .map(dividendMapper::toOpenDividend);

        return Stream.concat(openDividendsStream, closedDividendsStream).toList();

    }

    private List<Dividend> getClosedDividends(FlexQueryResponseDto dto) {
        return dto.nullSafeGetChangeInDividendAccruals()
                .stream()
                .filter(isValidClosedDividend())
                .map(dividendMapper::toClosedDividend)
                .toList();
    }

    @Override
    public UpdateDto parseAllData(FlexQueryResponseDto dto) {
        return UpdateDto.builder()
                .flexStatement(getFlexStatement(dto))
                .trades(getTrades(dto))
                .positions(getPositions(dto))
                .dividends(getDividends(dto))
                .build();
    }

    @Override
    public UpdateDto parseHistoricalData(FlexQueryResponseDto dto) {
        return UpdateDto.builder()
                .flexStatement(getFlexStatement(dto))
                .positions(Collections.emptyList())
                .trades(getTrades(dto))
                .dividends(getClosedDividends(dto))
                .build();
    }
}
