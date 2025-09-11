package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.EntityName;
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
public class StrategyUpdate {

  @NotNull
  @Schema(description = "Strategy id", example = "1")
  private Long id;

  @EntityName
  @Schema(
      description =
          "Strategy name must start with capital letter, contain 3-30 characters. Letters, numbers, "
              + "spaces, underscore and apostrophe allowed.",
      example = "AAPL bullcall Jan26")
  private String name;

  @Schema(
      description = "Strategy description",
      example = "Long-term bullish strategy on Apple stock")
  private String description;

  @Schema(description = "Portfolio id to assign the strategy to", example = "1")
  private Long portfolioId;
}
