package com.marcomarchionni.ibportfolio.services;

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
                if (StringUtils.hasText(op.getConid())) p.setConId(op.getConid());
                if (StringUtils.hasText(op.getSymbol())) p.setTicker(op.getSymbol());
                if (StringUtils.hasText(op.getPosition())) p.setQuantity(op.getPosition());
                if (StringUtils.hasText(op.getCostBasisPrice())) p.setCostBasisPrice(op.getCostBasisPrice());
                if (StringUtils.hasText(op.getMarkPrice())) p.setMarketPrice(op.getMarkPrice());
                result.add(p);
            }
            return result;
        } else if (type.equalsIgnoreCase("trade")) {
            List<Trade> result = new ArrayList<>();
            
            for (FlexQueryResponseDto.Trade tr : dto.getFlexStatements().get(0).getFlexStatement().get(0).getTrades().getTrade()) {
                Trade t = new Trade();
                if (StringUtils.hasText(tr.getTradeID())) t.setTradeId(tr.getTradeID());
                if (StringUtils.hasText(tr.getTradeDate()))t.setTradeDate(tr.getTradeDate());
                if (StringUtils.hasText(tr.getAssetCategory()))t.setAssetCategory(tr.getAssetCategory());
                if (StringUtils.hasText(tr.getSymbol()))t.setTicker(tr.getSymbol());
                if (StringUtils.hasText(tr.getPutCall()))t.setPutCall(tr.getPutCall());
                if (StringUtils.hasText(tr.getStrike()))t.setStrike(tr.getStrike());
                if (StringUtils.hasText(tr.getExpiry()))t.setExpiry(tr.getExpiry());
                if (StringUtils.hasText(tr.getMultiplier()))t.setMultiplier(tr.getMultiplier());
                if (StringUtils.hasText(tr.getBuySell()))t.setBuySell(tr.getBuySell());
                if (StringUtils.hasText(tr.getQuantity()))t.setQuantity(tr.getQuantity());
                if (StringUtils.hasText(tr.getTradePrice()))t.setTradePrice(tr.getTradePrice());
                result.add(t);
            }
            return result;
        } else {
            return null;
        }
    }
}
