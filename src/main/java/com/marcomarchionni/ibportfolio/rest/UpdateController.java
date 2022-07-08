package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateController {

    Updater updater;

    @Autowired
    public UpdateController(Updater updater) {
        this.updater = updater;
    }

    @Value("/flex/LastMonth.xml")
    Resource resource;

    @GetMapping("/file")
    public String updateFromFile() throws Exception{

        // TODO: Post Xml file via Http request
        File xmlFlexQuery = resource.getFile();

        updater.updateFromFile(xmlFlexQuery);
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
