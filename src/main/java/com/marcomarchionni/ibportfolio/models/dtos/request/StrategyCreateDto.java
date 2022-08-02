package com.marcomarchionni.ibportfolio.models.dtos.request;

import com.marcomarchionni.ibportfolio.models.validation.StrategyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
