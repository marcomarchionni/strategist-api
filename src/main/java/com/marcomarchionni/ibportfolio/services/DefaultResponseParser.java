package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DefaultResponseParser implements ResponseParser {

    /**
     * questo è un metodo con utilizzo di Generis. vuol dire che la risposta dipende dal chiamante
     * in particolare a seconda del type restituisce liste di posizioni o di trade
     * è un po' la copia di quello che avevi fatto tu, ma con tutti i controlli di consistenza sui campi, altrimenti a ogni campo vuoto o nullo hai eccezioni
     *
     * @param dto
     * @param type
     * @return
     */


    @Override
    public List parse(FlexQueryResponseDto dto, String type) {
        if (type.equalsIgnoreCase("position")) {
            List<Position> result = new ArrayList<>();
            for (FlexQueryResponseDto.OpenPosition op : dto.getFlexStatements().get(0).getFlexStatement().get(0).getOpenPositions().getOpenPosition()) {
                Position p = new Position();
                if (StringUtils.hasText(op.getConid())) p.setConid(op.getConid());
                if (StringUtils.hasText(op.getSymbol())) p.setSymbol(op.getSymbol());
                if (StringUtils.hasText(op.getPosition())) p.setQuantity(op.getPosition());
                if (StringUtils.hasText(op.getCostBasisPrice())) p.setCostBasisPrice(op.getCostBasisPrice());
                if (StringUtils.hasText(op.getMarkPrice())) p.setMarketPrice(op.getMarkPrice());
                if (StringUtils.hasText(op.getMultiplier())) p.setMultiplier(op.getMultiplier());
                result.add(p);
            }
            return result;
        }
        else if (type.equalsIgnoreCase("trade")) {
            List<Trade> result = new ArrayList<>();

            for (FlexQueryResponseDto.Trade tr : dto.getFlexStatements().get(0).getFlexStatement().get(0).getTrades().getTrade()) {
                Trade t = new Trade();
                if (StringUtils.hasText(tr.getTradeID())) t.setTradeId(tr.getTradeID());
                if (StringUtils.hasText(tr.getConid())) t.setConid(tr.getConid());
                if (StringUtils.hasText(tr.getTradeDate())) t.setTradeDate(tr.getTradeDate());
                if (StringUtils.hasText(tr.getAssetCategory())) t.setAssetCategory(tr.getAssetCategory());
                if (StringUtils.hasText(tr.getSymbol())) t.setSymbol(tr.getSymbol());
                if (StringUtils.hasText(tr.getPutCall())) t.setPutCall(tr.getPutCall());
                if (StringUtils.hasText(tr.getStrike())) t.setStrike(tr.getStrike());
                if (StringUtils.hasText(tr.getExpiry())) t.setExpiry(tr.getExpiry());
                if (StringUtils.hasText(tr.getMultiplier())) t.setMultiplier(tr.getMultiplier());
                if (StringUtils.hasText(tr.getBuySell())) t.setBuySell(tr.getBuySell());
                if (StringUtils.hasText(tr.getQuantity())) t.setQuantity(tr.getQuantity());
                if (StringUtils.hasText(tr.getTradePrice())) t.setTradePrice(tr.getTradePrice());
                if (StringUtils.hasText(tr.getTradeMoney())) t.setTradeMoney(tr.getTradeMoney());
                if (StringUtils.hasText(tr.getFifoPnlRealized())) t.setFifoPnlRealized(tr.getFifoPnlRealized());
                if (StringUtils.hasText(tr.getIbCommission())) t.setIbCommission(tr.getIbCommission());
                result.add(t);
            }
            return result;
        }
        else if (type.equalsIgnoreCase("dividend")) {
            List<Dividend> result = new ArrayList<>();

            for (FlexQueryResponseDto.ChangeInDividendAccrual dv : dto.getFlexStatements().get(0).getFlexStatement().get(0).getChangeInDividendAccruals().getChangeInDividendAccrual()){
                if (dv.getCode().equalsIgnoreCase("Re") && dv.getDate().equalsIgnoreCase(dv.getPayDate())) {
                    Dividend d = new Dividend();
                    if (StringUtils.hasText(dv.getConid())) d.setConid(dv.getConid());
                    if (StringUtils.hasText(dv.getSymbol())) d.setSymbol(dv.getSymbol());
                    if (StringUtils.hasText(dv.getExDate())) d.setExDate(dv.getExDate());
                    if (StringUtils.hasText(dv.getPayDate())) d.setPayDate(dv.getPayDate());
                    if (StringUtils.hasText(dv.getGrossRate())) d.setGrossRate(dv.getGrossRate());
                    if (StringUtils.hasText(dv.getQuantity())) d.setQuantity(dv.getQuantity());
                    if (StringUtils.hasText(dv.getGrossAmount())) d.setGrossAmount(dv.getGrossAmount());
                    if (StringUtils.hasText(dv.getTax())) d.setTax(dv.getTax());
                    if (StringUtils.hasText(dv.getNetAmount())) d.setNetAmount(dv.getNetAmount());
                    d.setDividendId(dv.getConid(), dv.getExDate());
                    d.setOpenClosed("CLOSED");
                    result.add(d);
                }
            }
            for (FlexQueryResponseDto.OpenDividendAccrual odv : dto.getFlexStatements().get(0).getFlexStatement().get(0).getOpenDividendAccruals().getOpenDividendAccrual()) {
                Dividend od = new Dividend();
                if (StringUtils.hasText(odv.getConid())) od.setConid(odv.getConid());
                if (StringUtils.hasText(odv.getSymbol())) od.setSymbol(odv.getSymbol());
                if (StringUtils.hasText(odv.getExDate())) od.setExDate(odv.getExDate());
                if (StringUtils.hasText(odv.getPayDate())) od.setPayDate(odv.getPayDate());
                if (StringUtils.hasText(odv.getGrossRate())) od.setGrossRate(odv.getGrossRate());
                if (StringUtils.hasText(odv.getQuantity())) od.setQuantity(odv.getQuantity());
                if (StringUtils.hasText(odv.getGrossAmount())) od.setGrossAmount(odv.getGrossAmount());
                if (StringUtils.hasText(odv.getTax())) od.setTax(odv.getTax());
                if (StringUtils.hasText(odv.getNetAmount())) od.setNetAmount(odv.getNetAmount());
                od.setDividendId(odv.getConid(), odv.getExDate());
                od.setOpenClosed("OPEN");
                result.add(od);
            }
            return result;
        } else {
            return null;
        }

    }
}
