package com.marcomarchionni.strategistapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="portfolio")
@Table(
        name = "portfolio",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "account_id"})
)
public class Portfolio implements AccountIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "name")
    private String name;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Strategy> strategies = new ArrayList<>();
}
