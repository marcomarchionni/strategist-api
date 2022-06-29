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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class DefaultResponseParser implements ResponseParser {

    static private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
    static private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd;hhmmss", Locale.ENGLISH);

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

        FlexQueryResponseDto.FlexStatement flexDto = dto.getFlexStatements().get(0).getFlexStatement().get(0);
        FlexStatement flexStatement = new FlexStatement();

        if (StringUtils.hasText(flexDto.getAccountId())) {
            flexStatement.setAccountId(flexDto.getAccountId());
        }
        if (StringUtils.hasText(flexDto.getFromDate())) {
            flexStatement.setFromDate(LocalDate.parse(flexDto.getFromDate(), dateFormatter));
        }
        if (StringUtils.hasText(flexDto.getToDate())) {
            flexStatement.setToDate(LocalDate.parse(flexDto.getToDate(), dateFormatter));
        }
        if (StringUtils.hasText(flexDto.getPeriod())) {
            flexStatement.setPeriod(flexDto.getPeriod());
        }
        if (StringUtils.hasText(flexDto.getWhenGenerated())) {
            flexStatement.setWhenGenerated(LocalDate.parse(flexDto.getWhenGenerated(), dateTimeFormatter));
        }
        return flexStatement;
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
            if (StringUtils.hasText(dtoTrade.getTradeID())) {
                trade.setTradeId(Long.parseLong(dtoTrade.getTradeID()));
            }
            if (StringUtils.hasText(dtoTrade.getConid())) {
                trade.setConId(Long.parseLong(dtoTrade.getConid()));
            }
            if (StringUtils.hasText(dtoTrade.getTradeDate())) {
                trade.setTradeDate(LocalDate.parse(dtoTrade.getTradeDate(), dateFormatter));
            }
            if (StringUtils.hasText(dtoTrade.getAssetCategory())) {
                trade.setAssetCategory(dtoTrade.getAssetCategory());
            }
            if (StringUtils.hasText(dtoTrade.getSymbol())) {
                trade.setSymbol(dtoTrade.getSymbol());
            }
            if (StringUtils.hasText(dtoTrade.getPutCall())) {
                trade.setPutCall(dtoTrade.getPutCall());
            }
            if (StringUtils.hasText(dtoTrade.getStrike())) {
                trade.setStrike(new BigDecimal(dtoTrade.getStrike()));
            }
            if (StringUtils.hasText(dtoTrade.getExpiry())) {
                trade.setExpiry(LocalDate.parse(dtoTrade.getExpiry(), dateFormatter));
            }
            if (StringUtils.hasText(dtoTrade.getMultiplier())) {
                trade.setMultiplier(Integer.parseInt(dtoTrade.getMultiplier()));
            }
            if (StringUtils.hasText(dtoTrade.getBuySell())) {
                trade.setBuySell(dtoTrade.getBuySell());
            }
            if (StringUtils.hasText(dtoTrade.getQuantity())) {
                trade.setQuantity(new BigDecimal(dtoTrade.getQuantity()));
            }
            if (StringUtils.hasText(dtoTrade.getTradePrice())) {
                trade.setTradePrice(new BigDecimal(dtoTrade.getTradePrice()));
            }
            if (StringUtils.hasText(dtoTrade.getTradeMoney())) {
                trade.setTradeMoney(new BigDecimal(dtoTrade.getTradeMoney()));
            }
            if (StringUtils.hasText(dtoTrade.getFifoPnlRealized())) {
                trade.setFifoPnlRealized(new BigDecimal(dtoTrade.getFifoPnlRealized()));
            }
            if (StringUtils.hasText(dtoTrade.getIbCommission())) {
                trade.setIbCommission(new BigDecimal(dtoTrade.getIbCommission()));
            }
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
            if (StringUtils.hasText(dtoPosition.getConid())) {
                position.setConId(Long.parseLong(dtoPosition.getConid()));
            }
            if (StringUtils.hasText(dtoPosition.getSymbol())) {
                position.setSymbol(dtoPosition.getSymbol());
            }
            if (StringUtils.hasText(dtoPosition.getPosition())) {
                position.setQuantity(new BigDecimal(dtoPosition.getPosition()));
            }
            if (StringUtils.hasText(dtoPosition.getCostBasisPrice())) {
                position.setCostBasisPrice(new BigDecimal(dtoPosition.getCostBasisPrice()));
            }
            if (StringUtils.hasText(dtoPosition.getMarkPrice())) {
                position.setMarketPrice(new BigDecimal(dtoPosition.getMarkPrice()));
            }
            if (StringUtils.hasText(dtoPosition.getMultiplier())) {
                position.setMultiplier(Integer.parseInt(dtoPosition.getMultiplier()));
            }
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
                if (StringUtils.hasText(dtoDividend.getConid())) {
                    dividend.setConId(Long.parseLong(dtoDividend.getConid()));
                }
                if (StringUtils.hasText(dtoDividend.getSymbol())) {
                    dividend.setSymbol(dtoDividend.getSymbol());
                }
                if (StringUtils.hasText(dtoDividend.getExDate())) {
                    dividend.setExDate(LocalDate.parse(dtoDividend.getExDate(), dateFormatter));
                }
                if (StringUtils.hasText(dtoDividend.getPayDate())) {
                    dividend.setPayDate(LocalDate.parse(dtoDividend.getPayDate(), dateFormatter));
                }
                if (StringUtils.hasText(dtoDividend.getGrossRate())) {
                    dividend.setGrossRate(new BigDecimal(dtoDividend.getGrossRate()));
                }
                if (StringUtils.hasText(dtoDividend.getQuantity())) {
                    dividend.setQuantity(new BigDecimal(dtoDividend.getQuantity()));
                }
                if (StringUtils.hasText(dtoDividend.getGrossAmount())) {
                    dividend.setGrossAmount(new BigDecimal(dtoDividend.getGrossAmount()).abs());
                }
                if (StringUtils.hasText(dtoDividend.getTax())) {
                    dividend.setTax(new BigDecimal(dtoDividend.getTax()).abs());
                }
                if (StringUtils.hasText(dtoDividend.getNetAmount())) {
                    dividend.setNetAmount(new BigDecimal(dtoDividend.getNetAmount()).abs());
                }
                dividend.setDividendId(Long.parseLong(dtoDividend.getConid() + dtoDividend.getExDate()));
                dividend.setOpenClosed("CLOSED");
                dividends.add(dividend);
            }

        }
        log.info("Parsed " + dtoDividends.size() + " dividend(s)");

        List<FlexQueryResponseDto.OpenDividendAccrual> dtoOpenDividends =
                dto.getFlexStatements().get(0).getFlexStatement().get(0).getOpenDividendAccruals().getOpenDividendAccrual();

        for (FlexQueryResponseDto.OpenDividendAccrual dtoOpenDividend : dtoOpenDividends) {
            Dividend openDividend = new Dividend();
            if (StringUtils.hasText(dtoOpenDividend.getConid())) {
                openDividend.setConId(Long.parseLong(dtoOpenDividend.getConid()));
            }
            if (StringUtils.hasText(dtoOpenDividend.getSymbol())) {
                openDividend.setSymbol(dtoOpenDividend.getSymbol());
            }
            if (StringUtils.hasText(dtoOpenDividend.getExDate())) {
                openDividend.setExDate(LocalDate.parse(dtoOpenDividend.getExDate(), dateFormatter));
            }
            if (StringUtils.hasText(dtoOpenDividend.getPayDate())) {
                openDividend.setPayDate(LocalDate.parse(dtoOpenDividend.getPayDate(), dateFormatter));
            }
            if (StringUtils.hasText(dtoOpenDividend.getGrossRate())) {
                openDividend.setGrossRate(new BigDecimal(dtoOpenDividend.getGrossRate()));
            }
            if (StringUtils.hasText(dtoOpenDividend.getQuantity())) {
                openDividend.setQuantity(new BigDecimal(dtoOpenDividend.getQuantity()));
            }
            if (StringUtils.hasText(dtoOpenDividend.getGrossAmount())) {
                openDividend.setGrossAmount(new BigDecimal(dtoOpenDividend.getGrossAmount()));
            }
            if (StringUtils.hasText(dtoOpenDividend.getTax())) {
                openDividend.setTax(new BigDecimal(dtoOpenDividend.getTax()));
            }
            if (StringUtils.hasText(dtoOpenDividend.getNetAmount())) {
                openDividend.setNetAmount(new BigDecimal(dtoOpenDividend.getNetAmount()));
            }
            openDividend.setDividendId(Long.parseLong(dtoOpenDividend.getConid() + dtoOpenDividend.getExDate()));
            openDividend.setOpenClosed("OPEN");
            dividends.add(openDividend);
        }
        log.info("Parsed " + dtoOpenDividends.size() + " open dividend(s)");

        return dividends;
    }
}
