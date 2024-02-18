package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.services.UpdateOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdateApi {

    private final UpdateOrchestrator updateOrchestrator;

    public CombinedUpdateReport update(@Valid UpdateContextReq dto) throws Exception {
        return updateOrchestrator.update(dto);
    }
}
