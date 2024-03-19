package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "1. Update", description = "Load trading data from an external source")
@RequestMapping("/update")
@SecurityRequirement(name = "bearerAuth")
public interface UpdateApi {
    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Update data from an external source")
    CombinedUpdateReport updateWithFile(
            @Parameter(description =
                    "SERVER: fetch data online from Interactive Brokers WebFlexService, " +
                            "FILE: fetch data from a flex query xml file, " +
                            "SAMPLEDATA: fetch sample data for testing purposes."
            )
            @RequestParam("sourceType") UpdateContext.SourceType sourceType,
            @Parameter(description = "Flex Query xml file generated from an Interactive Brokers user's account " +
                    "(ignored if sourceType is not FILE)")
            @RequestParam(value = "file", required = false) MultipartFile file,
            @Parameter(description = "Query id (ignored if sourceType is not SERVER)")
            @RequestParam(value = "queryId", required = false) String queryId,
            @Parameter(description = "Token (ignored if sourceType is not SERVER)")
            @RequestParam(value = "token", required = false) String token) throws Exception;

    @PostMapping()
    @Hidden
    CombinedUpdateReport updateWithoutFile(
            @RequestParam("sourceType") UpdateContext.SourceType sourceType,
            @RequestParam(value = "queryId", required = false) String queryId,
            @RequestParam(value = "token", required = false) String token) throws Exception;
}
