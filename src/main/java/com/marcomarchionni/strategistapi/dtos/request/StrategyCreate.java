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
public class StrategyCreate {

  @EntityName
  @Schema(
      description =
          "Strategy name must start with capital letter, contain 3-30 characters. Letters, numbers, "
              + "spaces, underscore and apostrophe allowed.",
      example = "AAPL bullcall Jan26")
  private String name;

  @NotNull
  @Schema(description = "Portfolio id. Should refer to an existing portfolio", example = "1")
  private Long portfolioId;

  @Schema(
      description = "Strategy description",
      example = "Long-term bullish strategy on Apple stock")
  private String description;
}
