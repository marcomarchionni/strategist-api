package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.services.DividendService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DividendController implements DividendApi {

  private final DividendService dividendService;

  public List<DividendSummary> findByFilter(@Valid DividendFind dividendFind) {
    return dividendService.findByFilter(dividendFind);
  }

  public DividendSummary updateStrategyId(@RequestBody @Valid StrategyAssign dividendUpdate) {
    return dividendService.updateStrategyId(dividendUpdate);
  }
}
