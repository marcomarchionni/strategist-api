package com.marcomarchionni.ibportfolio.models.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStrategyDto {

    @NotNull
    private Long id;

    @NotNull
    private Long strategyId;
}
