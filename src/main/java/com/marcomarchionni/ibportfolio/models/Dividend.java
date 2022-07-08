package com.marcomarchionni.ibportfolio.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name="dividend")
public class Dividend {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="con_id")
    private Long conId;

    @Column(name="symbol")
    private String symbol;

    @Column(name="description")
    private String description;

    @Column(name="ex_date")
    private LocalDate exDate;

    @Column(name="pay_date")
    private LocalDate payDate;

    @Column(name="gross_rate")
    private BigDecimal grossRate;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="gross_amount")
    private BigDecimal grossAmount;

    @Column(name="tax")
    private BigDecimal tax;

    @Column(name="net_amount")
    private BigDecimal netAmount;

    @Column(name="open_closed")
    private String openClosed;
}
