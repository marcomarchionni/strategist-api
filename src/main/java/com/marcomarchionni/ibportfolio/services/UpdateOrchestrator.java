package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UpdateOrchestrator {
    CombinedUpdateReport updateFromServer() throws IOException;

    CombinedUpdateReport updateFromFile(MultipartFile file) throws IOException;
}
