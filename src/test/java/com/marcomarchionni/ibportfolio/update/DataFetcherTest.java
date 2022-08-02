package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.update.flexDtos.FlexQueryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataFetcherTest {

    DataFetcher dataFetcher;
    File flexQuery;

    @BeforeEach
    void setup() {
        dataFetcher = new DataFetcher();
        ClassLoader classLoader = getClass().getClassLoader();
        flexQuery = new File(Objects.requireNonNull(classLoader.getResource("flex/LastMonth.xml")).getFile());
    }

    @Test
    void fetchFromFile() throws Exception {

        assertNotNull(flexQuery);

        FlexQueryResponseDto dto = dataFetcher.fetchFromFile(flexQuery);

        assertNotNull(dto);
        String accountId = dto.getFlexStatements().get(0).getFlexStatement().get(0).getAccountId();
        assertEquals("U7169936", accountId);
    }
}