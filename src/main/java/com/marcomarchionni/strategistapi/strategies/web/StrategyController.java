package com.marcomarchionni.strategistapi.strategies.web;

import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.StrategyUpdate;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.strategies.service.StrategyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StrategyController implements StrategyApi {

  private final StrategyService strategyService;

  public ApiResponse<StrategySummary> findAll(
      @RequestParam(defaultValue = "0") int skip,
      @RequestParam(defaultValue = "10") int top,
      @RequestParam(required = false) String orderBy,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String portfolioName,
      @RequestParam(required = false) String createdAfter,
      @RequestParam(required = false) String createdBefore) {

    StrategyFindAllReq findReq =
        StrategyFindAllReq.builder()
            .skip(skip)
            .top(top)
            .orderBy(orderBy)
            .name(name)
            .description(description)
            .portfolioName(portfolioName)
            .createdAfter(createdAfter)
            .createdBefore(createdBefore)
            .build();

    return strategyService.findAllWithCount(findReq);
  }

  public StrategyDetail findById(@PathVariable Long id) {
    return strategyService.findById(id);
  }

  public StrategyDetail create(@RequestBody @Valid StrategyCreate strategyCreate) {
    return strategyService.create(strategyCreate);
  }

  public StrategyDetail update(@RequestBody @Valid StrategyUpdate strategyUpdate) {
    return strategyService.update(strategyUpdate);
  }

  public void delete(@PathVariable Long id) {
    strategyService.deleteById(id);
  }
}
