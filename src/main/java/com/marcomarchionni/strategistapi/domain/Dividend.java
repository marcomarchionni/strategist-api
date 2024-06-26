package com.marcomarchionni.strategistapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dividend")
@Table(name = "dividend", uniqueConstraints = @UniqueConstraint(columnNames = {"action_id", "account_id"}))
public class Dividend implements AccountIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "con_id")
    private Long conId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "action_id")
    private Long actionId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "dividend_strategy_id")
    private Strategy strategy;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "ex_date")
    private LocalDate exDate;

    @Column(name = "pay_date")
    private LocalDate payDate;

    @Column(name = "gross_rate")
    private BigDecimal grossRate;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "open_closed")
    @Enumerated(EnumType.STRING)
    private OpenClosed openClosed;

    public enum OpenClosed {
        OPEN, CLOSED
    }
}
