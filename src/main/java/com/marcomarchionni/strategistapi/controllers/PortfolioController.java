package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.BatchOperationService;
import com.marcomarchionni.strategistapi.services.PortfolioService;
import com.marcomarchionni.strategistapi.services.parsers.BatchRequestParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios/")
public class PortfolioController implements PortfolioApi {

    private final PortfolioService portfolioService;
    private final BatchRequestParser batchRequestParser;
    private final BatchOperationService batchOperationService;

    @GetMapping
    public ApiResponse<PortfolioSummary> findAll(@RequestParam(value = "$inlinecount", required = false) String inlineCount) {
        var results = portfolioService.findAll();
        var count = results.size();
        return ApiResponse.<PortfolioSummary>builder().result(results).count(count).build();
    }

    @GetMapping("/{id}")
    public PortfolioDetail findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @PostMapping("/$batch")
    public ResponseEntity<String> handleBatchRequest(HttpServletRequest request) throws Exception {
        List<BatchOperation<PortfolioSave>> operations = batchRequestParser.parseRequest(request, PortfolioSave.class);
        log.info("Operations: {}", operations);
        batchOperationService.executeBatchOperations(operations);
        return ResponseEntity.ok().body("Batch request processed");
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
