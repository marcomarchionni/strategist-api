package com.marcomarchionni.ibportfolio.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "trade")
public class Trade {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="trade_id")
    private Long tradeId;

    @Column(name="con_id")
    private Long conId;

    @Column(name="trade_strategy_id")
    private Long strategy_id;

    @Column(name="symbol")
    private String symbol;

    @Column(name="description")
    private String description;

    @Column(name="asset_category")
    private String assetCategory;

    @Column(name="multiplier")
    private Integer multiplier;

    @Column(name="strike")
    private BigDecimal strike;

    @Column(name="expiry")
    private LocalDate expiry;

    @Column(name="put_call")
    private String putCall;

    @Column(name="trade_date")
    private LocalDate tradeDate;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="trade_price")
    private BigDecimal tradePrice;

    @Column(name="trade_money")
    private BigDecimal tradeMoney;

    @Column(name="fifo_pnl_realized")
    private BigDecimal fifoPnlRealized;

    @Column(name="ib_commission")
    private BigDecimal ibCommission;

    @Column(name="buy_sell")
    private String buySell;
}
