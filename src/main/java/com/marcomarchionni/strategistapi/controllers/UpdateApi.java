package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
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
            @RequestParam("sourceType") String sourceType,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "queryId", required = false) String queryId,
            @RequestParam(value = "token", required = false) String token) throws Exception;

    @PostMapping()
    @Hidden
    CombinedUpdateReport updateWithoutFile(
            @RequestParam("sourceType") String sourceType,
            @RequestParam(value = "queryId", required = false) String queryId,
            @RequestParam(value = "token", required = false) String token) throws Exception;
}
