package com.marcomarchionni.strategistapi.dtos.response;

import com.marcomarchionni.strategistapi.validators.EntityName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyDetail {

  @NotNull
  @Schema(description = "Strategy id", example = "1")
  private Long id;

  @EntityName
  @Schema(
      description =
          "Strategy name must start with capital letter, contain 3-30 characters. Letters, numbers, "
              + "spaces, underscore and apostrophe allowed",
      example = "AAPL bullcall Jan26")
  private String name;

  @NotNull
  @Schema(description = "Portfolio id", example = "1")
  private Long portfolioId;

  @EntityName
  @Schema(
      description =
          "Portfolio name must start with capital letter, contain 3-30 characters. Letters, numbers, "
              + "spaces, underscore and apostrophe allowed",
      example = "Rule Makers")
  private String portfolioName;

  @NotNull
  @Schema(description = "Account id", example = "U1111111")
  private String accountId;

  @NotNull
  @Schema(description = "Strategy creation date", example = "2024-01-15")
  private LocalDate createdAt;

  @Schema(
      description = "Strategy description",
      example = "Long-term bullish strategy on Apple stock")
  private String description;

  @NotNull private List<TradeSummary> trades;

  @NotNull private List<PositionSummary> positions;

  @NotNull private List<DividendSummary> dividends;
}
