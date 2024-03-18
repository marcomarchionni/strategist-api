package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.services.UpdateOrchestrator;
import com.marcomarchionni.strategistapi.validators.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdateApi {

    private final UpdateOrchestrator updateOrchestrator;
    private final DtoValidator<UpdateContext> contextValidator;

    public CombinedUpdateReport updateWithFile(
            @RequestParam("sourceType") String sourceType,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "queryId", required = false) String queryId,
            @RequestParam(value = "token", required = false) String token
    ) throws Exception {
        return update(UpdateContext.builder()
                .sourceType(sourceType)
                .file(file)
                .queryId(queryId)
                .token(token)
                .build());
    }

    @Override
    public CombinedUpdateReport updateWithoutFile(String sourceType, String queryId, String token) throws Exception {
        return update(UpdateContext.builder()
                .sourceType(sourceType)
                .queryId(queryId)
                .token(token)
                .build());
    }

    private CombinedUpdateReport update(UpdateContext context) throws IOException {
        contextValidator.validate(context);
        return updateOrchestrator.update(context);
    }
}
