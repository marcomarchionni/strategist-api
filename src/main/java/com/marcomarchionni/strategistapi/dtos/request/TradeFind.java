package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.AssetCategory;
import com.marcomarchionni.strategistapi.validators.DateInterval;
import com.marcomarchionni.strategistapi.validators.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateInterval(
        dateFrom = "tradeDateAfter",
        dateTo = "tradeDateBefore",
        message = "tradeDateAfter, tradeDateBefore should be within Min and Max date. tradeDateAfter should be equal " +
                "or before tradeDateBefore")
@Schema(description = "Trade filter. Each field is optional")
public class TradeFind {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Trade date after", example = "2021-01-01")
    private LocalDate tradeDateAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Trade date before", example = "2024-02-02")
    private LocalDate tradeDateBefore;

    @Schema(description = "Trade assigned to a strategy", example = "false")
    private Boolean tagged;

    @Size(max = 5)
    @NullOrNotBlank
    @Schema(description = "Symbol", example = "AAPL")
    private String symbol;

    @AssetCategory
    @Schema(description = "Asset category. Allowed values: STK, OPT, FUT, CASH", example = "STK")
    private String assetCategory;
}
