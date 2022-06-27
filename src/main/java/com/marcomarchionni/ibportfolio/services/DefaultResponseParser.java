package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.update.FlexQueryData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DefaultResponseParser implements ResponseParser {

    @Override
    public FlexQueryData parse(FlexQueryResponseDto dto) {

        FlexQueryData flexQueryData = new FlexQueryData();
        flexQueryData.setFlexStatement(parseFlexStatement(dto));
        flexQueryData.setTrades(parseTrades(dto));
        flexQueryData.setPositions(parsePositions(dto));
        flexQueryData.setTrades(parseTrades(dto));
        flexQueryData.setDividends(parseDividends(dto));
        return flexQueryData;
    }


    private FlexStatement parseFlexStatement (FlexQueryResponseDto dto) {
        // TODO: set fields in FlexStatement
        return new FlexStatement();
    }

    /**
     * extract trades from dto
     * @param dto map of FlexQueryResponse
     * @return list of trades
     */
    private List<Trade> parseTrades(FlexQueryResponseDto dto) {

        List<Trade> trades = new ArrayList<>();
        List<FlexQueryResponseDto.Trade> dtoTrades =
                dto.getFlexStatements().get(0).getFlexStatement().get(0).getTrades().getTrade();

        for (FlexQueryResponseDto.Trade dtoTrade : dtoTrades) {
            Trade trade = new Trade();
            if (StringUtils.hasText(dtoTrade.getTradeID())) trade.setTradeId(dtoTrade.getTradeID());
            if (StringUtils.hasText(dtoTrade.getConid())) trade.setConid(dtoTrade.getConid());
            if (StringUtils.hasText(dtoTrade.getTradeDate())) trade.setTradeDate(dtoTrade.getTradeDate());
            if (StringUtils.hasText(dtoTrade.getAssetCategory())) trade.setAssetCategory(dtoTrade.getAssetCategory());
            if (StringUtils.hasText(dtoTrade.getSymbol())) trade.setSymbol(dtoTrade.getSymbol());
            if (StringUtils.hasText(dtoTrade.getPutCall())) trade.setPutCall(dtoTrade.getPutCall());
            if (StringUtils.hasText(dtoTrade.getStrike())) trade.setStrike(dtoTrade.getStrike());
            if (StringUtils.hasText(dtoTrade.getExpiry())) trade.setExpiry(dtoTrade.getExpiry());
            if (StringUtils.hasText(dtoTrade.getMultiplier())) trade.setMultiplier(dtoTrade.getMultiplier());
            if (StringUtils.hasText(dtoTrade.getBuySell())) trade.setBuySell(dtoTrade.getBuySell());
            if (StringUtils.hasText(dtoTrade.getQuantity())) trade.setQuantity(dtoTrade.getQuantity());
            if (StringUtils.hasText(dtoTrade.getTradePrice())) trade.setTradePrice(dtoTrade.getTradePrice());
            if (StringUtils.hasText(dtoTrade.getTradeMoney())) trade.setTradeMoney(dtoTrade.getTradeMoney());
            if (StringUtils.hasText(dtoTrade.getFifoPnlRealized())) trade.setFifoPnlRealized(dtoTrade.getFifoPnlRealized());
            if (StringUtils.hasText(dtoTrade.getIbCommission())) trade.setIbCommission(dtoTrade.getIbCommission());
            trades.add(trade);
        }
        log.info("Parsed " + dtoTrades.size() + " trade(s)");

        return trades;
    }

    /**
     * extract positions from dto
     * @param dto map of FlexQueryResponse
     * @return list of positions
     */
    private List<Position> parsePositions(FlexQueryResponseDto dto) {

        List<Position> positions = new ArrayList<>();
        List<FlexQueryResponseDto.OpenPosition> dtoPositions =
                dto.getFlexStatements().get(0).getFlexStatement().get(0).getOpenPositions().getOpenPosition();

        for (FlexQueryResponseDto.OpenPosition dtoPosition : dtoPositions) {
            Position position = new Position();
            if (StringUtils.hasText(dtoPosition.getConid())) position.setConid(dtoPosition.getConid());
            if (StringUtils.hasText(dtoPosition.getSymbol())) position.setSymbol(dtoPosition.getSymbol());
            if (StringUtils.hasText(dtoPosition.getPosition())) position.setQuantity(dtoPosition.getPosition());
            if (StringUtils.hasText(dtoPosition.getCostBasisPrice())) position.setCostBasisPrice(dtoPosition.getCostBasisPrice());
            if (StringUtils.hasText(dtoPosition.getMarkPrice())) position.setMarketPrice(dtoPosition.getMarkPrice());
            if (StringUtils.hasText(dtoPosition.getMultiplier())) position.setMultiplier(dtoPosition.getMultiplier());
            positions.add(position);
        }
        log.info("Parsed " + dtoPositions.size() + " position(s)");

        return positions;
    }

    /**
     * extract dividends and open dividends from dto
     * @param dto map of FlexQueryResponse
     * @return list of dividends
     */
    private List<Dividend> parseDividends(FlexQueryResponseDto dto) {

        List<Dividend> dividends = new ArrayList<>();
        List<FlexQueryResponseDto.ChangeInDividendAccrual> dtoDividends =
                dto.getFlexStatements().get(0).getFlexStatement().get(0).getChangeInDividendAccruals().getChangeInDividendAccrual();

        for (FlexQueryResponseDto.ChangeInDividendAccrual dtoDividend : dtoDividends) {
            if (dtoDividend.getCode().equalsIgnoreCase("Re") && dtoDividend.getDate().equalsIgnoreCase(dtoDividend.getPayDate())) {
                Dividend dividend = new Dividend();
                if (StringUtils.hasText(dtoDividend.getConid())) dividend.setConid(dtoDividend.getConid());
                if (StringUtils.hasText(dtoDividend.getSymbol())) dividend.setSymbol(dtoDividend.getSymbol());
                if (StringUtils.hasText(dtoDividend.getExDate())) dividend.setExDate(dtoDividend.getExDate());
                if (StringUtils.hasText(dtoDividend.getPayDate())) dividend.setPayDate(dtoDividend.getPayDate());
                if (StringUtils.hasText(dtoDividend.getGrossRate())) dividend.setGrossRate(dtoDividend.getGrossRate());
                if (StringUtils.hasText(dtoDividend.getQuantity())) dividend.setQuantity(dtoDividend.getQuantity());
                if (StringUtils.hasText(dtoDividend.getGrossAmount())) dividend.setGrossAmount(dtoDividend.getGrossAmount());
                if (StringUtils.hasText(dtoDividend.getTax())) dividend.setTax(dtoDividend.getTax());
                if (StringUtils.hasText(dtoDividend.getNetAmount())) dividend.setNetAmount(dtoDividend.getNetAmount());
                dividend.setDividendId(dtoDividend.getConid(), dtoDividend.getExDate());
                dividend.setOpenClosed("CLOSED");
                dividends.add(dividend);
            }

        }
        log.info("Parsed " + dtoDividends.size() + " dividend(s)");

        List<FlexQueryResponseDto.OpenDividendAccrual> dtoOpenDividends =
                dto.getFlexStatements().get(0).getFlexStatement().get(0).getOpenDividendAccruals().getOpenDividendAccrual();

        for (FlexQueryResponseDto.OpenDividendAccrual dtoOpenDividend : dtoOpenDividends) {
            Dividend openDividend = new Dividend();
            if (StringUtils.hasText(dtoOpenDividend.getConid())) openDividend.setConid(dtoOpenDividend.getConid());
            if (StringUtils.hasText(dtoOpenDividend.getSymbol())) openDividend.setSymbol(dtoOpenDividend.getSymbol());
            if (StringUtils.hasText(dtoOpenDividend.getExDate())) openDividend.setExDate(dtoOpenDividend.getExDate());
            if (StringUtils.hasText(dtoOpenDividend.getPayDate())) openDividend.setPayDate(dtoOpenDividend.getPayDate());
            if (StringUtils.hasText(dtoOpenDividend.getGrossRate())) openDividend.setGrossRate(dtoOpenDividend.getGrossRate());
            if (StringUtils.hasText(dtoOpenDividend.getQuantity())) openDividend.setQuantity(dtoOpenDividend.getQuantity());
            if (StringUtils.hasText(dtoOpenDividend.getGrossAmount())) openDividend.setGrossAmount(dtoOpenDividend.getGrossAmount());
            if (StringUtils.hasText(dtoOpenDividend.getTax())) openDividend.setTax(dtoOpenDividend.getTax());
            if (StringUtils.hasText(dtoOpenDividend.getNetAmount())) openDividend.setNetAmount(dtoOpenDividend.getNetAmount());
            openDividend.setDividendId(dtoOpenDividend.getConid(), dtoOpenDividend.getExDate());
            openDividend.setOpenClosed("OPEN");
            dividends.add(openDividend);
        }
        log.info("Parsed " + dtoOpenDividends.size() + " open dividend(s)");

        return dividends;
    }
}
