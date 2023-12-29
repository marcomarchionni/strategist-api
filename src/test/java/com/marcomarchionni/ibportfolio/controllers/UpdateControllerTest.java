package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.services.JwtService;
import com.marcomarchionni.ibportfolio.services.UpdateOrchestrator;
import com.marcomarchionni.ibportfolio.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrades;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UpdateController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateControllerTest {

    @MockBean
    UpdateOrchestrator updateOrchestrator;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @Test
    void updateFromFile() throws Exception {
        // Load the file from classpath
        MockMultipartFile mockFile;

        try (InputStream stream = getClass().getResourceAsStream("flex/Flex.xml")) {
            mockFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "Flex.xml", // filename
                    "text/xml", // content type
                    stream // file content
            );
        }

        assertNotNull(mockFile);

        // setup UpdateOrchestrator mock
        List<Trade> addedTrades = getSampleTrades();
        UpdateReport<Trade> tradeReport = UpdateReport.<Trade>builder().added(addedTrades).build();
        CombinedUpdateReport combinedUpdateReport = CombinedUpdateReport.builder().trades(tradeReport).build();
        when(updateOrchestrator.updateFromFile(any(MultipartFile.class))).thenReturn(combinedUpdateReport);

        // setup userService mock
        when(userService.getAuthenticatedUser()).thenReturn(getSampleUser());


        ArgumentCaptor<MockMultipartFile> mockMultipartFileArgumentCaptor =
                ArgumentCaptor.forClass(MockMultipartFile.class);

        // Mock the updateFromFile method
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update/from-file")
                        .file(mockFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());


        // Verify that the method was called with the captured InputStream
        verify(updateOrchestrator).updateFromFile(mockMultipartFileArgumentCaptor.capture());

        // Check if multipart file is not null
        assertNotNull(mockMultipartFileArgumentCaptor.getValue());

        // Compare the contents
        assertEquals(mockFile, mockMultipartFileArgumentCaptor.getValue());
    }

    @Test
    void updateFromServer() throws Exception {

        // setup UpdateOrchestrator mock
        List<Trade> addedTrades = getSampleTrades();
        UpdateReport<Trade> tradeReport = UpdateReport.<Trade>builder().added(addedTrades).build();
        CombinedUpdateReport combinedUpdateReport = CombinedUpdateReport.builder().trades(tradeReport).build();
        when(updateOrchestrator.updateFromServer()).thenReturn(combinedUpdateReport);

        // setup userService mock
        when(userService.getAuthenticatedUser()).thenReturn(getSampleUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/update/from-server"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());

        // Verify that the method was called
        verify(updateOrchestrator).updateFromServer();
    }

}