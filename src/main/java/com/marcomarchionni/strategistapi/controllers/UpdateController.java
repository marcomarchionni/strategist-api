package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.services.UpdateOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/update")
public class UpdateController {

    private final UpdateOrchestrator updateOrchestrator;

    @PostMapping()
    public CombinedUpdateReport update(@Valid UpdateContextDto dto) throws Exception {
        return updateOrchestrator.update(dto);
    }
}
