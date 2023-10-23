package com.marcomarchionni.ibportfolio.services;

import java.io.IOException;
import java.io.InputStream;

public interface UpdateOrchestrator {
    void updateFromFile(InputStream stream) throws IOException;

    void updateFromServer() throws IOException;
}
