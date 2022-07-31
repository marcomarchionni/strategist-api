package com.marcomarchionni.ibportfolio.models.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateStrategyDto {

    @NotNull
    private Long id;

    @NotNull
    private Long strategyId;
}
