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
    private Long con_id;
    private String symbol;
    private String asset_category;
    private Integer multiplier;
    private BigDecimal strike;
    private LocalDate expiry;
    private String putCall;
    private LocalDate trade_date;
    private BigDecimal quantity;
    private BigDecimal trade_price;
    private BigDecimal trade_money;
    private BigDecimal fifo_pnl_realized;
    private BigDecimal ib_commission;
    private String buy_sell;
    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public void setConid(String conid) {
        this.con_id = Long.parseLong(conid);
    }

    public void setTradeMoney(String tradeMoney) {
        this.trade_money = new BigDecimal(tradeMoney);
    }

    public void setFifoPnlRealized(String fifoPnlRealized) {
        this.fifo_pnl_realized = new BigDecimal(fifoPnlRealized);
    }

    public void setIbCommission(String ibCommission) {
        this.ib_commission = new BigDecimal(ibCommission);
    }

    public BigDecimal getTrade_money() {
        return trade_money;
    }

    public BigDecimal getFifo_pnl_realized() {
        return fifo_pnl_realized;
    }

    public BigDecimal getIb_commission() {
        return ib_commission;
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public String getSymbol() {
        return symbol;
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
        return trade_id + " " + trade_date + " " + symbol + " " + asset_category + " " + buy_sell + " " + quantity + " " + trade_price;
    }
}
