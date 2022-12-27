package com.marcomarchionni.ibportfolio.model.domain;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "position")
public class Position {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="con_id")
    private Long conId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name= "position_strategy_id")
    private Strategy strategy;

    @Column(name="report_date")
    private LocalDate reportDate;

    @Column(name="symbol")
    private String symbol;

    @Column(name="description")
    private String description;

    @Column(name = "asset_category")
    private String assetCategory;

    @Column(name = "put_call")
    private String putCall;

    @Column(name= "strike")
    private BigDecimal strike;

    @Column(name = "expiry")
    private LocalDate expiry;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="cost_basis_price")
    private BigDecimal costBasisPrice;

    @Column(name="cost_basis_money")
    private BigDecimal costBasisMoney;

    @Column(name="mark_price")
    private BigDecimal markPrice;

    @Column(name="multiplier")
    private int multiplier;

    @Column(name="position_value")
    private BigDecimal positionValue;

    @Column(name="fifo_pnl_unrealized")
    private BigDecimal fifoPnlUnrealized;
}