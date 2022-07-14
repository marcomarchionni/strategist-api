package com.marcomarchionni.ibportfolio.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    @Column(name="market_price")
    private BigDecimal marketPrice;

    @Column(name="multiplier")
    private int multiplier;
}