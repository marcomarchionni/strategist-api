package com.marcomarchionni.ibportfolio.services;


import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class IBHandler extends DefaultHandler {

    private List<Position> positions;
    private List<Trade> trades;
    private Position currentPosition;
    private Trade currentTrade;

    public List<Position> getPositions() {
        return positions;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    @Override
    public void startDocument() {
        positions = new ArrayList<>();
        trades = new ArrayList<>();
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        // start of loop
        if (qName.equalsIgnoreCase("OpenPosition")) {

            // new Position
            currentPosition = new Position();

            // set ConId
            String conId = attributes.getValue("conid");
            currentPosition.setConId(conId);

            // set Ticker
            String ticker = attributes.getValue("symbol");
            currentPosition.setTicker(ticker);

            // set Position
            String quantity = attributes.getValue("position");
            currentPosition.setQuantity(quantity);

            // set BuyPrice
            String costBasisPrice = attributes.getValue("costBasisPrice");
            currentPosition.setCostBasisPrice(costBasisPrice);

            // set MarketPrice
            String marketPrice = attributes.getValue("markPrice");
            currentPosition.setMarketPrice(marketPrice);

            /* set PositionValue
            String positionValue = attributes.getValue("positionValue");
            currentPosition.setPositionValue(positionValue);*/
        }
        if (qName.equalsIgnoreCase("Trade")) {

            //New Trade
            currentTrade = new Trade();

            // set tradeId
            String tradeId = attributes.getValue("tradeID");
            currentTrade.setTradeId(tradeId);

            // set tradeDate
            String tradeDate = attributes.getValue("tradeDate");
            currentTrade.setTradeDate(tradeDate);

            // set assetCategory
            String assetCategory = attributes.getValue("assetCategory");
            currentTrade.setAssetCategory(assetCategory);

            // set ticker
            String ticker = attributes.getValue("symbol");
            currentTrade.setTicker(ticker);

            // set putCall
            String putCall = attributes.getValue("putCall");
            currentTrade.setPutCall(putCall);

            // set strike
            String strike = attributes.getValue("strike");
            currentTrade.setStrike(strike);

            // set expiry
            String expiry = attributes.getValue("expiry");
            currentTrade.setExpiry(expiry);

            // set multiplier
            String multiplier = attributes.getValue("multiplier");
            currentTrade.setMultiplier(multiplier);

            // set buySell
            String buySell = attributes.getValue("buySell");
            currentTrade.setBuySell(buySell);

            // set quantity
            String quantity = attributes.getValue("quantity");
            currentTrade.setQuantity(quantity);

            // set tradePrice
            String tradePrice = attributes.getValue("tradePrice");
            currentTrade.setTradePrice(tradePrice);
        }

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {

        // end of loop
        if (qName.equalsIgnoreCase("OpenPosition")) {
            positions.add(currentPosition);
        }

        if (qName.equalsIgnoreCase("Trade")) {
            trades.add(currentTrade);
        }

    }

}