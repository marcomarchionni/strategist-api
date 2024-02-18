package com.marcomarchionni.strategistapi.dtos.response;

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

    private Long id;
    private Long strategyId;
    private String strategyName;
    private String symbol;
    private String description;
    private String assetCategory;
    private Integer multiplier;
    private BigDecimal strike;
    private LocalDate expiry;
    private String putCall;
    private LocalDate tradeDate;
    private BigDecimal quantity;
    private BigDecimal tradePrice;
    private BigDecimal tradeMoney;
    private BigDecimal fifoPnlRealized;
    private BigDecimal ibCommission;
    private String buySell;
}