package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.BatchReport;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.BatchOperationService;
import com.marcomarchionni.strategistapi.services.PortfolioService;
import com.marcomarchionni.strategistapi.services.parsers.BatchRequestParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioController implements PortfolioApi {

    private final PortfolioService portfolioService;
    private final BatchRequestParser batchRequestParser;
    private final BatchOperationService batchOperationService;

    public ApiResponse<PortfolioSummary> findAll(@Valid FindAllReq findReq) {
        var results = portfolioService.findAllWithPaging(findReq);
        var count = portfolioService.getTotalCount();
        return ApiResponse.<PortfolioSummary>builder()
                .result(results)
                .count(count)
                .build();
    }

    public PortfolioDetail findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    public BatchReport<PortfolioSummary> handleBatchRequest(HttpServletRequest request) throws Exception {
        List<BatchOperation<PortfolioSave>> operations = batchRequestParser.parseRequest(request, PortfolioSave.class);
        log.info("Operations: {}", operations);
        return batchOperationService.executeBatchOperations(operations);
    }

    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
