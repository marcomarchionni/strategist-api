package com.marcomarchionni.ibportfolio.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
