package com.marcomarchionni.ibportfolio.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="strategy")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name= "strategy_portfolio_id")
    private Integer portfolioId;

    @Column(name = "strategy_name")
    private String strategyName;
}
