package com.marcomarchionni.ibportfolio.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "positions")
public class Position {
    @Id
    private Long con_id;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal cost_basis_price;
    private BigDecimal market_price;
    private int multiplier;
    //private BigDecimal positionValue;

    public Position() {
    }

    public void setConid(String conid) {
        this.con_id = Long.parseLong(conid);
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(String quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public void setCostBasisPrice(String costBasisPrice) {
        this.cost_basis_price = new BigDecimal(costBasisPrice);
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = Integer.parseInt(multiplier);
    }

    public void setMarketPrice(String marketPrice) {
        this.market_price = new BigDecimal(marketPrice);
    }

    /*public void setPositionValue(String positionValue){
        this.positionValue = new BigDecimal(positionValue);
    }*/

    public String toString() {
        return symbol + " " + quantity + " " + cost_basis_price + " " + market_price + " " + multiplier;
    }
}