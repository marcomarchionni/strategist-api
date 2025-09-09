package com.marcomarchionni.strategistapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StrategyFindAllReq {

  @Builder.Default private int skip = 0;

  @Builder.Default private int top = 10;

  private String orderBy;

  // Strategy-specific filter parameters
  private String name;
  private String description;
  private String portfolioName;
  private String createdAfter;
  private String createdBefore;
}
