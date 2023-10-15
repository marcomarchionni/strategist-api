package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.FileUpdater;
import com.marcomarchionni.ibportfolio.update.LiveUpdater;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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
