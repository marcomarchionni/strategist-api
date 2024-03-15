package com.marcomarchionni.strategistapi.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionSummary {

    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "Contract id", example = "10564")
    private Long conId;

    @Schema(description = "Strategy id", example = "1")
    private Long strategyId;

    @Schema(description = "Strategy name", example = "AAPL bullcall Jan26")
    private String strategyName;

    @Schema(description = "Symbol", example = "AAPL")
    private String symbol;

    @Schema(description = "Description", example = "AAPL 2026-01-15 150.0 Call")
    private String description;

    @Schema(description = "Asset category", example = "OPT")
    private String assetCategory;

    @Schema(description = "Put or call", example = "CALL")
    private String putCall;

    @Schema(description = "Strike", example = "150")
    private BigDecimal strike;

    @Schema(description = "Expiry", example = "2026-01-15")
    private LocalDate expiry;

    @Schema(description = "Quantity", example = "1")
    private BigDecimal quantity;

    @Schema(description = "Cost basis price", example = "1.5")
    private BigDecimal costBasisPrice;

    @Schema(description = "Cost basis money", example = "150.0")
    private BigDecimal costBasisMoney;

    @Schema(description = "Market price", example = "1.6")
    private BigDecimal markPrice;

    @Schema(description = "Multiplier", example = "100")
    private int multiplier;

    @Schema(description = "Position value", example = "160.0")
    private BigDecimal positionValue;

    @Schema(description = "Profit and Loss Unrealized", example = "10.0")
    private BigDecimal fifoPnlUnrealized;
}
