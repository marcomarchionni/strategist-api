package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateController {

    Updater updater;

    @Autowired
    public UpdateController(Updater updater) {
        this.updater = updater;
    }

    @GetMapping("/file")
    public String updateFromFile() {
        updater.updateFromFile();
        log.info(">>> Update from file started...");
        return "Update from file started...";
    }

    @GetMapping("/server")
    public String updateFromServer() {
        updater.updateFromServer();
        log.info(">>> Update from server started...");
        return "Update from server started...";
    }

}
