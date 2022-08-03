package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Objects;

@RestController
@RequestMapping("/update")
public class UpdateController {

    Updater updater;

    @Autowired
    public UpdateController(Updater updater) {
        this.updater = updater;
    }

    @GetMapping("/file")
    public String updateFromFile() throws Exception{

        // TODO: Where do you get xmlfiles?
        updater.updateFromFile(getFile());
        return "Update from file completed";
    }

    @GetMapping("/server")
    public String updateFromServer() {
        updater.updateFromServer();
        return "Update from server started...";
    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("flex/LastMonth.xml")).getFile());
    }
}
