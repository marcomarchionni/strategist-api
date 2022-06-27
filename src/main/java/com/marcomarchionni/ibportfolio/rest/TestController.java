package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

    Updater dataFetch;

    public TestController(Updater dataFetch) {
        this.dataFetch = dataFetch;
    }

    @GetMapping("/update")
    public String updatePortfolio() {
        //updater.fetchFromServer();
        log.info(">>> Update started from web request...");
        return "Update started...";
    }


}
