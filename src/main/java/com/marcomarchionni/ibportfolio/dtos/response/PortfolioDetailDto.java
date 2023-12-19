package com.marcomarchionni.ibportfolio.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDetailDto {

    private Long id;
    private String accountId;
    private String name;
    private List<StrategySummaryDto> strategies;
}
