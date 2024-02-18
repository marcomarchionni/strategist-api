package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;

import java.io.IOException;

public interface UpdateOrchestrator {
    CombinedUpdateReport update(UpdateContextReq dto) throws IOException;
}
