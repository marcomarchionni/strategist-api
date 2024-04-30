package com.marcomarchionni.strategistapi.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummary {

    @Schema(description = "Portfolio id", example = "1")
    private Long id;
    @Schema(description = "Account id", example = "U1111111")
    private String accountId;
    @Schema(description = "Portfolio creation date", example = "2021-01-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @Schema(description = "Portfolio name", example = "Rule Makers")
    private String name;
    @Schema(description = "Portfolio description", example = "Rule Makers portfolio")
    private String description;
}
