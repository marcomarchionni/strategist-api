package com.marcomarchionni.ibportfolio.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="portfolio")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Portfolio name should not be blank")
    @Column(name="name", unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<Strategy> strategies;
}
