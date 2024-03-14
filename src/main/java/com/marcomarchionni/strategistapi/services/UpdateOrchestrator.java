package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;

import java.io.IOException;

public interface UpdateOrchestrator {
    CombinedUpdateReport update(UpdateContext dto) throws IOException;
}
