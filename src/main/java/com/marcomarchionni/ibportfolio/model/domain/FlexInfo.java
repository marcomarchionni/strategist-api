package com.marcomarchionni.ibportfolio.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name="flex_info")
public class FlexInfo {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_id")
    private String accountId;

    @Column(name="from_date")
    private LocalDate fromDate;

    @Column(name="to_date")
    private LocalDate toDate;

    @Column(name="period")
    private String period;

    @Column(name="when_generated")
    private LocalDate whenGenerated;
}
