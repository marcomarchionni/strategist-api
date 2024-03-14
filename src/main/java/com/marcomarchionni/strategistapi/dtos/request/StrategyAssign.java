package com.marcomarchionni.strategistapi.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyAssign {

    @NotNull(message = "'id' cannot be null.")
    @Schema(description = "Trade id", example = "1")
    private Long id;

    @NotNull(message = "'strategyId' cannot be null.")
    @Schema(description = "Strategy id", example = "1")
    private Long strategyId;
}
