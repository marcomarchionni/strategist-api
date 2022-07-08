package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataFetcherTest {

    @Value("classpath:/flex/LastMonth.xml")
    Resource flexQueryResource;

    @Autowired
    DataFetcher dataFetcher;

    @Test
    void fetchFromFile() throws Exception {

        File flexQuery = flexQueryResource.getFile();
        assertNotNull(flexQuery);
        assertTrue(flexQuery.isFile());

        FlexQueryResponseDto dto = dataFetcher.fetchFromFile(flexQuery);

        assertNotNull(dto);
        String accountId = dto.getFlexStatements().get(0).getFlexStatement().get(0).getAccountId();
        assertEquals(accountId, "U7169936");
    }
}