package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleUser;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateControllerIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User user = getSampleUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void updateFromFile() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/Flex.xml")) {
            MockMultipartFile mockFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "Flex.xml", // filename
                    "text/xml", // content type
                    stream // file content
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/update")
                            .file(mockFile)
                            .param("sourceType", "FILE"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.trades").isNotEmpty());
        }
    }

    @Test
    void updateFromFileWithNoExtension() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/NoExtension")) {
            MockMultipartFile mockFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "NoExtension", // filename
                    "text/xml", // content type
                    stream // file content
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/update")
                            .file(mockFile)
                            .param("sourceType", "FILE"))

                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.type").value("no-xml-extension"));
        }
    }

    @Test
    void updateFromFileMalformedXml() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("flex/Malformed.xml")) {
            MockMultipartFile mockFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "Malformed.xml", // filename
                    "text/xml", // content type
                    stream // file content
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/update")
                            .file(mockFile)
                            .param("sourceType", "FILE"))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.type").value("invalid-xml-file"));
        }
    }

    @Test
    void updateWithSampleData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update")
                        .param("sourceType", "SAMPLEDATA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").isNotEmpty());
    }
}