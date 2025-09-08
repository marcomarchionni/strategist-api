package com.marcomarchionni.strategistapi.strategies.web;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
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
      @RequestParam(required = false) String portfolioName) {

    FindAllReq findReq =
        FindAllReq.builder()
            .skip(skip)
            .top(top)
            .orderBy(orderBy)
            .name(name)
            .description(portfolioName) // reusing description field to transport portfolioName
            .build();

    return strategyService.findAllWithCount(findReq);
  }

  public StrategyDetail findById(@PathVariable Long id) {
    return strategyService.findById(id);
  }

  public StrategyDetail create(@RequestBody @Valid StrategyCreate strategyCreate) {
    return strategyService.create(strategyCreate);
  }

  public StrategyDetail updateName(@RequestBody @Valid NameUpdate nameUpdate) {
    return strategyService.updateName(nameUpdate);
  }

  public void delete(@PathVariable Long id) {
    strategyService.deleteById(id);
  }
}
