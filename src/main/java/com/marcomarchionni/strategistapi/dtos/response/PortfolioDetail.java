package com.marcomarchionni.strategistapi.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDetail {

    @Schema(description = "Portfolio id", example = "1")
    private Long id;
    @Schema(description = "Account id", example = "U1111111")
    private String accountId;
    @Schema(description = "Portfolio name", example = "Rule Makers")
    private String name;
    @Schema(description = "Portfolio creation date", example = "2021-01-01")
    private String createdAt;
    @Schema(description = "Portfolio description", example = "Rule Makers portfolio")
    private String description;
    @Schema(description = "List of strategies in the portfolio")
    private List<StrategySummary> strategies;
}
