package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.services.UpdateOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdateApi {

    private final UpdateOrchestrator updateOrchestrator;

    public CombinedUpdateReport update(@Valid UpdateContext updateContext) throws Exception {
        return updateOrchestrator.update(updateContext);
    }
}
