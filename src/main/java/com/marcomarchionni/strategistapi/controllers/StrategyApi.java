package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "4. Strategies", description = "Create or edit user defined strategies")
@RequestMapping("/strategies")
@SecurityRequirement(name = "bearerAuth")
public interface StrategyApi {

    @GetMapping
    @Operation(summary = "Find strategies by filter",
            parameters = {
                    @Parameter(name = "name", description = "Strategy name", example = "AAPL bullcall Jan26", schema
                            = @Schema(type = "string")),
            })
    @Parameter(name = "strategyFind", hidden = true)
    List<StrategySummary> findByFilter(@Valid StrategyFind strategyFind);

    @GetMapping("/{id}")
    @Operation(summary = "Find strategy by id")
    StrategyDetail findById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create a new strategy")
    StrategyDetail create(@RequestBody @Valid StrategyCreate strategyCreate);

    @PutMapping
    @Operation(summary = "Update strategy name")
    StrategyDetail updateName(@RequestBody @Valid NameUpdate nameUpdate);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete strategy by id")
    void delete(@PathVariable Long id);
}
