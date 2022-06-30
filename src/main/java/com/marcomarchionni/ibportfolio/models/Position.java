package com.marcomarchionni.ibportfolio.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "position")
public class Position {

    @Id
    @Column(name="con_id")
    private Long conId;

    @Column(name="report_date")
    private LocalDate reportDate;

    @Column(name="symbol")
    private String symbol;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="cost_basis_price")
    private BigDecimal costBasisPrice;

    @Column(name="market_price")
    private BigDecimal marketPrice;

    @Column(name="multiplier")
    private int multiplier;

    public Position() {
    }
}