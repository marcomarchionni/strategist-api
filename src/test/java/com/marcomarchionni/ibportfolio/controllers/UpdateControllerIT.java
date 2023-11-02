package com.marcomarchionni.ibportfolio.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateControllerIT {

    @Autowired
    MockMvc mockMvc;

    MockMultipartFile mockFile;

    @BeforeEach
    void setUp() throws IOException {
        Resource fileResource = new ClassPathResource("flex/SimpleJune2022.xml");
        mockFile = new MockMultipartFile(
                "file", // the name of the parameter
                "SimpleFlex.xml", // filename
                "text/xml", // content type
                fileResource.getInputStream() // file content
        );

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateFromFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update/from-file")
                        .file(mockFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());
    }
}