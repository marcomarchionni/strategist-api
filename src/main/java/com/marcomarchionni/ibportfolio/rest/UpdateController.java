package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.FileUpdater;
import com.marcomarchionni.ibportfolio.update.Updater;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Objects;

@RestController
@RequestMapping("/update")
public class UpdateController {

    Updater updater;

    FileUpdater fileUpdater;

    public UpdateController(Updater updater, FileUpdater fileUpdater) {
        this.updater = updater;
        this.fileUpdater = fileUpdater;
    }

    @GetMapping("/file")
    public String updateFromFile() throws Exception {

        // TODO: Get xmlfilefrom @RequestParam or @RequestBody
        fileUpdater.update(loadFile());
        return "Update from file completed";
    }

    @GetMapping("/server")
    public String updateFromServer() {
        updater.updateFromServer();
        return "Update from server started...";
    }

    private File loadFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("flex/SimpleJune2022.xml")).getFile());
    }
}
