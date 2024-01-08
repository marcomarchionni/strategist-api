package com.marcomarchionni.strategistapi.dtos.request;

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

    @NotNull(message = "'id' cannot be null.")
    private Long id;

    @NotNull(message = "'strategyId' cannot be null.")
    private Long strategyId;
}
