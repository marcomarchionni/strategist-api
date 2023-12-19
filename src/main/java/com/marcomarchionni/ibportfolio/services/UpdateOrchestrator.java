package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UpdateOrchestrator {

    CombinedUpdateReport updateFromServer(User user) throws IOException;

    CombinedUpdateReport updateFromFile(User user, MultipartFile file) throws IOException;
}
