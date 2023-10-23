package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UploadedFileException;
import com.marcomarchionni.ibportfolio.update.FileUpdater;
import com.marcomarchionni.ibportfolio.update.LiveUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/update")
public class UpdateController {

    LiveUpdater liveUpdater;

    FileUpdater fileUpdater;

    public UpdateController(LiveUpdater liveUpdater, FileUpdater fileUpdater) {
        this.liveUpdater = liveUpdater;
        this.fileUpdater = fileUpdater;
    }

    @PostMapping("/file")
    public ResponseEntity<String> updateFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new UploadedFileException();
        }
        InputStream xmlStream = file.getInputStream();
        fileUpdater.update(xmlStream);
        return ResponseEntity.ok("Update from file completed");

    }

    @GetMapping("/file")
    public String updateFromFile() throws Exception {

        // TODO: Get xmlfilefrom @RequestParam or @RequestBody
        fileUpdater.update(loadFile());
        return "Update from file completed";
    }

    @GetMapping("/server")
    public String updateFromServer() {
        liveUpdater.update();
        return "Update from server completed";
    }

    private File loadFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("flex/SimpleJune2022.xml")).getFile());
    }
}
