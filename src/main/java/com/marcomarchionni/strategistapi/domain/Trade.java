package com.marcomarchionni.strategistapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "trade")
public class Trade implements AccountIdEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "trade_id")
    private Long tradeId;

    @Column(name = "ib_order_id")
    private Long ibOrderId;

    @Column(name = "con_id")
    private Long conId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "trade_strategy_id")
    private Strategy strategy;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "asset_category")
    private String assetCategory;

    @Column(name = "multiplier")
    private Integer multiplier;

    @Column(name = "strike")
    private BigDecimal strike;

    @Column(name = "expiry")
    private LocalDate expiry;

    @Column(name = "put_call")
    private String putCall;

    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "trade_price")
    private BigDecimal tradePrice;

    @Column(name = "trade_money")
    private BigDecimal tradeMoney;

    @Column(name = "fifo_pnl_realized")
    private BigDecimal fifoPnlRealized;

    @Column(name = "ib_commission")
    private BigDecimal ibCommission;

    @Column(name = "buy_sell")
    private String buySell;
}
