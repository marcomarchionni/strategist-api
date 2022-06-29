package com.marcomarchionni.ibportfolio;

import com.marcomarchionni.ibportfolio.update.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    private final Updater updater;

    public StartupRunner(Updater updater) {
        this.updater = updater;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> Starting updating from file");
        updater.updateFromFile();
//        updater.updateFromServer();

    }
}
