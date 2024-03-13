package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "2. Update", description = "Load trading data from an external source")
@RequestMapping("/update")
@SecurityRequirement(name = "bearerAuth")
public interface UpdateApi {
    @PostMapping()
    @Operation(summary = "Update data from an external source")
    CombinedUpdateReport update(@Valid UpdateContextReq dto) throws Exception;
}
