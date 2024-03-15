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
public class DividendSummary {

    @Schema(description = "id", example = "1")
    private Long id;
    @Schema(description = "Contract id", example = "10564")
    private Long conId;
    @Schema(description = "Strategy Id", example = "1")
    private Long strategyId;
    @Schema(description = "Strategy name", example = "AAPL bullcall Jan26")
    private String strategyName;
    @Schema(description = "Symbol", example = "AAPL")
    private String symbol;
    @Schema(description = "Description", example = "AAPL 2026-01-15 150.0 Call")
    private String description;
    @Schema(description = "exDate", example = "2024-01-15")
    private LocalDate exDate;
    @Schema(description = "payDate", example = "2024-01-27")
    private LocalDate payDate;
    @Schema(description = "Gross dividend rate", example = "0.5")
    private BigDecimal grossRate;
    @Schema(description = "Quantity", example = "100")
    private BigDecimal quantity;
    @Schema(description = "Gross amount", example = "50.0")
    private BigDecimal grossAmount;
    @Schema(description = "Tax", example = "5.0")
    private BigDecimal tax;
    @Schema(description = "Net amount", example = "45.0")
    private BigDecimal netAmount;
    @Schema(description = "Open or closed", example = "CLOSED")
    private String openClosed;
}
