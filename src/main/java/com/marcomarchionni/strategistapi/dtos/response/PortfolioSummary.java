package com.marcomarchionni.strategistapi.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummary {

    @Schema(description = "Portfolio id", example = "1")
    private Long id;
    @Schema(description = "Account id", example = "U1111111")
    private String accountId;
    @Schema(description = "Portfolio name", example = "Rule Makers")
    private String name;
}
