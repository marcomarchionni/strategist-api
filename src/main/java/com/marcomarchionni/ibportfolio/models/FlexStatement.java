package com.marcomarchionni.ibportfolio.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="flex_statement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlexStatement {
    @Id
    @Column(name="flex_statement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flexStatementId;

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
