package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.services.UpdateOrchestrator;
import com.marcomarchionni.ibportfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/update")
public class UpdateController {

    private final UpdateOrchestrator updateOrchestrator;
    private final UserService userService;

    @PostMapping("/from-file")
    public CombinedUpdateReport updateFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        User user = userService.getAuthenticatedUser();
        return updateOrchestrator.updateFromFile(user, file);
    }

    @PostMapping("/from-server")
    public ResponseEntity<CombinedUpdateReport> updateFromServer() throws Exception {
        User user = userService.getAuthenticatedUser();
        CombinedUpdateReport report = updateOrchestrator.updateFromServer(user);
        return ResponseEntity.ok(report);
    }
}
