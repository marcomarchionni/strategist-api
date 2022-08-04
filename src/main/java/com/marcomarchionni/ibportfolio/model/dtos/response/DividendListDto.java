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
public class DividendListDto {

    private Long id;
    private Long conId;
    private Long strategyId;
    private String strategyName;
    private String symbol;
    private String description;
    private LocalDate exDate;
    private LocalDate payDate;
    private BigDecimal grossRate;
    private BigDecimal quantity;
    private BigDecimal grossAmount;
    private BigDecimal tax;
    private BigDecimal netAmount;
    private String openClosed;
}
