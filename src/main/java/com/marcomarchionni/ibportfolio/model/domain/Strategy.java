package com.marcomarchionni.ibportfolio.model.domain;

import com.marcomarchionni.ibportfolio.model.validation.StrategyName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Entity(name="strategy")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name= "strategy_portfolio_id")
    private Portfolio portfolio;

    @StrategyName
    @Column(name = "name", unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    private List<Dividend> dividends = new ArrayList<>();

    public Strategy(Long id, Portfolio portfolio, String name, List<Position> positions, List<Trade> trades, List<Dividend> dividends) {
        this.id = id;
        this.name = name;
        this.portfolio = portfolio;
        if (trades != null) this.trades = trades;
        if (positions != null) this.positions = positions;
        if (dividends != null) this.dividends = dividends;
    }
}
