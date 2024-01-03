package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.dtos.validators.StrategyName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyCreateDto {

    @StrategyName
    private String name;

    @NotNull
    private Long portfolioId;
}
