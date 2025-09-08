package com.marcomarchionni.strategistapi.portfolios.web;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.portfolios.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class PortfolioController implements PortfolioApi {

  private final PortfolioService portfolioService;

  public ApiResponse<PortfolioSummary> findAll(
      @RequestParam(defaultValue = "0") int skip,
      @RequestParam(defaultValue = "10") int top,
      @RequestParam(required = false) String orderBy,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String createdAfter,
      @RequestParam(required = false) String createdBefore) {

    // Build FindAllReq object from query parameters
    FindAllReq findReq =
        FindAllReq.builder()
            .skip(skip)
            .top(top)
            .orderBy(orderBy)
            .name(name)
            .description(description)
            .createdAfter(createdAfter)
            .createdBefore(createdBefore)
            .build();

    return portfolioService.findAllWithCount(findReq);
  }

  public PortfolioDetail findById(@PathVariable Long id) {
    return portfolioService.findById(id);
  }

  public PortfolioSummary createPortfolio(@RequestBody @Valid PortfolioSave portfolioSave) {
    return portfolioService.create(portfolioSave);
  }

  public void deletePortfolio(@PathVariable Long id) {
    portfolioService.deleteById(id);
  }

  public PortfolioSummary updatePortfolio(
      @PathVariable Long id, @RequestBody @Valid PortfolioSave portfolioSave) {
    portfolioSave.setId(id); // Ensure the ID is set from the path
    return portfolioService.update(portfolioSave);
  }
}
