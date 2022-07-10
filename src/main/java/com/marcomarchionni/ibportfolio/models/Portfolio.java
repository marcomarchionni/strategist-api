package com.marcomarchionni.ibportfolio.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="portfolio_name")
    private String portfolioName;
}
