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
public class TradeSummary {

    @Schema(description = "id", example = "1")
    private Long id;
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
    @Schema(description = "Multiplier", example = "100")
    private Integer multiplier;
    @Schema(description = "Strike", example = "150")
    private BigDecimal strike;
    @Schema(description = "Expiry", example = "2026-01-15")
    private LocalDate expiry;
    @Schema(description = "Put or call", example = "CALL")
    private String putCall;
    @Schema(description = "Trade date", example = "2022-01-15")
    private LocalDate tradeDate;
    @Schema(description = "Quantity", example = "1")
    private BigDecimal quantity;
    @Schema(description = "Trade price", example = "1.5")
    private BigDecimal tradePrice;
    @Schema(description = "Trade money", example = "150.0")
    private BigDecimal tradeMoney;
    @Schema(description = "Profit or loss realized", example = "0")
    private BigDecimal fifoPnlRealized;
    @Schema(description = "ibCommission", example = "0.1")
    private BigDecimal ibCommission;
    @Schema(description = "Bought or sold", example = "BUY")
    private String buySell;
}
