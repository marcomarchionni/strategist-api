package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.dtos.update.CombinedUpdateReport;

import java.io.IOException;

public interface UpdateOrchestrator {
    CombinedUpdateReport update(UpdateContextDto dto) throws IOException;
}
