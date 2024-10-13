package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.response.BatchReport;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Portfolios", description = "Create or edit user defined portfolios.")
@RequestMapping("/portfolios")
@SecurityRequirement(name = "bearerAuth")
public interface PortfolioApi {
    @GetMapping("/")
    @Operation(summary = "Find all user's portfolios")
    Object findAll(FindAllReq findAllReq);

    @GetMapping("/{id}")
    @Operation(summary = "Find portfolio by id")
    PortfolioDetail findById(@PathVariable Long id);

    @PostMapping("/$batch")
    @Operation(summary = "Create a new portfolio")
    BatchReport handleBatchRequest(HttpServletRequest request) throws Exception;

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio by id")
    void deletePortfolio(@PathVariable Long id);
}
