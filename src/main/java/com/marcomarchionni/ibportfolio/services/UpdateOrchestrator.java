package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;

import java.io.IOException;
import java.io.InputStream;

public interface UpdateOrchestrator {
    CombinedUpdateReport updateFromFile(InputStream stream) throws IOException;

    CombinedUpdateReport updateFromServer() throws IOException;
}
