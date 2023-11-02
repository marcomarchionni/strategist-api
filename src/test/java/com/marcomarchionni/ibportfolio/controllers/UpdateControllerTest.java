package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.services.UpdateOrchestrator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrades;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UpdateController.class)
class UpdateControllerTest {

    @MockBean
    UpdateOrchestrator updateOrchestrator;

    @Autowired
    MockMvc mockMvc;

    @Test
    void updateFromFileInvalidFile() throws Exception {
        Resource fileResource = new ClassPathResource("flex/InvalidFlex");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", // the name of the parameter
                "InvalidFile", // filename
                "text/xml", // content type
                fileResource.getInputStream() // file content
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/update/from-file")
                        .file(mockFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void updateFromFile() throws Exception {

        // Load the file from classpath
        Resource fileResource = new ClassPathResource("flex/SimpleJune2022.xml");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", // the name of the parameter
                "SimpleFlex.xml", // filename
                "text/xml", // content type
                fileResource.getInputStream() // file content
        );


        // setup UpdateOrchestrator mock
        List<Trade> addedTrades = getSampleTrades();
        UpdateReport<Trade> tradeReport = UpdateReport.<Trade>builder().added(addedTrades).build();
        CombinedUpdateReport combinedUpdateReport = CombinedUpdateReport.builder().trades(tradeReport).build();
        when(updateOrchestrator.updateFromFile(any(InputStream.class))).thenReturn(combinedUpdateReport);


        ArgumentCaptor<InputStream> inputStreamCaptor = ArgumentCaptor.forClass(InputStream.class);

        // Mock the updateFromFile method
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update/from-file")
                        .file(mockFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());


        // Verify that the method was called with the captured InputStream
        verify(updateOrchestrator).updateFromFile(inputStreamCaptor.capture());

        // Check if InputStream is not null
        assertNotNull(inputStreamCaptor.getValue());

        // Compare the contents
        byte[] capturedContent = StreamUtils.copyToByteArray(inputStreamCaptor.getValue());
        byte[] expectedContent = Files.readAllBytes(Paths.get("src/test/resources/flex/SimpleFlex.xml"));

        assertArrayEquals(expectedContent, capturedContent);
    }

    @Test
    void updateFromServer() throws Exception {

        // setup UpdateOrchestrator mock
        List<Trade> addedTrades = getSampleTrades();
        UpdateReport<Trade> tradeReport = UpdateReport.<Trade>builder().added(addedTrades).build();
        CombinedUpdateReport combinedUpdateReport = CombinedUpdateReport.builder().trades(tradeReport).build();
        when(updateOrchestrator.updateFromServer()).thenReturn(combinedUpdateReport);

        mockMvc.perform(MockMvcRequestBuilders.post("/update/from-server"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());

        // Verify that the method was called
        verify(updateOrchestrator).updateFromServer();
    }

}