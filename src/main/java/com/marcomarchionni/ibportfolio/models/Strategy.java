package com.marcomarchionni.ibportfolio.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="strategy")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name= "strategy_portfolio_id")
    private Portfolio portfolio;

    @Column(name = "strategy_name")
    private String strategyName;
}
