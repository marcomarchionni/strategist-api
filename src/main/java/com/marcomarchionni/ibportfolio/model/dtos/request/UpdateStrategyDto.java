package com.marcomarchionni.ibportfolio.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

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
