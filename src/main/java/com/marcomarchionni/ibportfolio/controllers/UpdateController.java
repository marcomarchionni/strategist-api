package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.response.ApiResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UploadedFileException;
import com.marcomarchionni.ibportfolio.services.UpdateOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/update")
public class UpdateController {

    UpdateOrchestrator updateOrchestrator;

    public UpdateController(UpdateOrchestrator updateOrchestrator) {
        this.updateOrchestrator = updateOrchestrator;
    }

    @PostMapping("/from-file")
    public ResponseEntity<ApiResponseDto> updateFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new UploadedFileException();
        }
        InputStream xmlStream = file.getInputStream();
        updateOrchestrator.updateFromFile(xmlStream);
        return ResponseEntity.ok().body(new ApiResponseDto(200, "Upload from file completed"));
    }

    @PostMapping("/from-server")
    public ResponseEntity<ApiResponseDto> updateFromFile() throws Exception {
        updateOrchestrator.updateFromServer();
        return ResponseEntity.ok(new ApiResponseDto(200, "Upload from server completed"));
    }
}
