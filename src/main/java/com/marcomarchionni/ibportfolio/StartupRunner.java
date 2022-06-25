package com.marcomarchionni.ibportfolio;

import com.marcomarchionni.ibportfolio.scheduler.DataFetch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    private final DataFetch dataFetch;

    public StartupRunner(DataFetch dataFetch) {
        this.dataFetch = dataFetch;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> Starting method dataFetch in class FetchData");
        dataFetch.fetchData();

    }
}