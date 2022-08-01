package com.marcomarchionni.ibportfolio.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.marcomarchionni.ibportfolio.models.validation.PortfolioName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Entity(name="portfolio")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @PortfolioName
    @Column(name="name", unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<Strategy> strategies = new ArrayList<>();

    public Portfolio(Long id, String name, List<Strategy> strategies) {
        this.id = id;
        this.name = name;
        if(strategies != null) this.strategies = strategies;
    }
}
