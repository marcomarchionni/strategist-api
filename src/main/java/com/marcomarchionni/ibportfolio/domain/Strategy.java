package com.marcomarchionni.ibportfolio.domain;

import com.marcomarchionni.ibportfolio.controllers.validators.StrategyName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Entity(name="strategy")
@Table(
        name = "strategy",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "account_id"})
)
public class Strategy implements AccountIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name= "strategy_portfolio_id")
    private Portfolio portfolio;

    @StrategyName
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Position> positions = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Trade> trades = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Dividend> dividends = new ArrayList<>();

    public Strategy(Long id, String accountId, Portfolio portfolio, String name, List<Position> positions,
                    List<Trade> trades, List<Dividend> dividends) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.portfolio = portfolio;
        if (trades != null) this.trades = trades;
        if (positions != null) this.positions = positions;
        if (dividends != null) this.dividends = dividends;
    }
}
