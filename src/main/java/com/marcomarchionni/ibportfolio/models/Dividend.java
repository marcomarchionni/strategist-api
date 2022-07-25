package com.marcomarchionni.ibportfolio.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity(name="dividend")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="open_closed",
        discriminatorType = DiscriminatorType.STRING)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dividend {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="con_id")
    private Long conId;

    @Column(name="dividend_strategy_id")
    private Long strategyId;

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

    @Column(name="open_closed", insertable = false, updatable = false)
    private String openClosed;
}
