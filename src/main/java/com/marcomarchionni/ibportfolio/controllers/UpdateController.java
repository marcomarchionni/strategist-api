package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.services.UpdateOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/update")
public class UpdateController {

    UpdateOrchestrator updateOrchestrator;

    public UpdateController(UpdateOrchestrator updateOrchestrator) {
        this.updateOrchestrator = updateOrchestrator;
    }

    @PostMapping("/from-file")
    public CombinedUpdateReport updateFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        return updateOrchestrator.updateFromFile(file);
    }

    @PostMapping("/from-server")
    public ResponseEntity<CombinedUpdateReport> updateFromServer() throws Exception {
        CombinedUpdateReport report = updateOrchestrator.updateFromServer();
        return ResponseEntity.ok(report);
    }
}
