package com.marcomarchionni.ibportfolio.model.dtos.response;

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
public class PositionListDto {

    private Long id;
    private Long conId;
    private Long strategyId;
    private String strategyName;
    private String symbol;
    private String description;
    private String assetCategory;
    private String putCall;
    private BigDecimal strike;
    private LocalDate expiry;
    private BigDecimal quantity;
    private BigDecimal costBasisPrice;
    private BigDecimal costBasisMoney;
    private BigDecimal markPrice;
    private int multiplier;
    private BigDecimal positionValue;
    private BigDecimal fifoPnlUnrealized;
}
