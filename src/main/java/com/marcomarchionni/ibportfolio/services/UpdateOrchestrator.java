package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;

import java.io.IOException;

public interface UpdateOrchestrator {
    CombinedUpdateReport update(UpdateContextDto dto) throws IOException;
}
