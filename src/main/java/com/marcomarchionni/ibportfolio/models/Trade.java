package com.marcomarchionni.ibportfolio.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity(name = "trades")
public class Trade {
    @Id
    private Long trade_id;
    private String ticker;
    private String asset_category;
    private Integer multiplier;
    private BigDecimal strike;
    private LocalDate expiry;
    private String putCall;
    private LocalDate trade_date;
    private BigDecimal quantity;
    private BigDecimal trade_price;
    private String buy_sell;
    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setQuantity(String quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public void setPutCall(String putCall) {
        this.putCall = putCall;
    }

    public void setAssetCategory(String assetCategory) {
        this.asset_category = assetCategory;
    }

    public void setTradeId(String tradeId) {
        this.trade_id = Long.parseLong(tradeId);
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = Integer.parseInt(multiplier);
    }

    public void setStrike(String strike) {
        this.strike = new BigDecimal(strike);
    }

    public void setExpiry(String expiry) {
        this.expiry = LocalDate.parse(expiry, formatter);
    }

    public void setTradeDate(String tradeDate) {
        this.trade_date = LocalDate.parse(tradeDate, formatter);
    }

    public void setBuySell(String buySell) {
        this.buy_sell = buySell;
    }

    public void setTradePrice(String tradePrice) {
        this.trade_price = new BigDecimal(tradePrice);
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public String getTicker() {
        return ticker;
    }

    public String getAsset_category() {
        return asset_category;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public BigDecimal getStrike() {
        return strike;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public String getPutCall() {
        return putCall;
    }

    public LocalDate getTrade_date() {
        return trade_date;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTrade_price() {
        return trade_price;
    }

    public String getBuy_sell() {
        return buy_sell;
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public String toString() {
        return trade_id + " " + trade_date + " " + ticker + " " + asset_category + " " + buy_sell + " " + quantity + " " + trade_price;
    }
}
