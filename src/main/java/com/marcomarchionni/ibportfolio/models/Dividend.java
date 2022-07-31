package com.marcomarchionni.ibportfolio.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="dividend")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="open_closed",
        discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Dividend {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="con_id")
    private Long conId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name= "dividend_strategy_id")
    private Strategy strategy;

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
