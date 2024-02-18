package com.marcomarchionni.strategistapi.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDetail {

    private Long id;
    private String accountId;
    private String name;
    private List<StrategySummary> strategies;
}
