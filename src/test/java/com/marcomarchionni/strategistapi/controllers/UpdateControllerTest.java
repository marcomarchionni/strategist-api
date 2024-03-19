package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.UpdateOrchestrator;
import com.marcomarchionni.strategistapi.validators.DtoValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UpdateController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateControllerTest {

    @MockBean
    UpdateOrchestrator updateOrchestrator;

    @MockBean
    DtoValidator<UpdateContext> updateContextDtoValidator;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    CombinedUpdateReport combinedUpdateReport;

    MockMultipartFile mockFile;

    ArgumentCaptor<UpdateContext> contextDtoArgumentCaptor;

    @BeforeEach
    void setUp() throws IOException {
        // setup UpdateReport
        List<TradeSummary> addedTrades = List.of(
                TradeSummary.builder().id(1L).build(),
                TradeSummary.builder().id(2L).build()
        );
        UpdateReport<TradeSummary> tradeReport = UpdateReport.<TradeSummary>builder().added(addedTrades).build();
        combinedUpdateReport = CombinedUpdateReport.builder().trades(tradeReport).build();

        // setup mock multipart file
        try (InputStream stream = getClass().getResourceAsStream("flex/Flex.xml")) {
            mockFile = new MockMultipartFile(
                    "file", // the name of the parameter
                    "Flex.xml", // filename
                    "text/xml", // content type
                    stream // file content
            );
        }
        // setup contextDtoArgumentCaptor
        contextDtoArgumentCaptor =
                ArgumentCaptor.forClass(UpdateContext.class);
    }

    @Test
    void updateFromFile() throws Exception {
        // setup UpdateContext
        UpdateContext contextDto = UpdateContext.builder()
                .sourceType(UpdateContext.SourceType.FILE)
                .file(mockFile)
                .build();

        // setup UpdateOrchestrator mock
        when(updateOrchestrator.update(contextDto)).thenReturn(combinedUpdateReport);

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update")
                        .file(mockFile)
                        .param("sourceType", "FILE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());


        // Verify that the method was called with the captured InputStream
        verify(updateOrchestrator).update(contextDtoArgumentCaptor.capture());

        // Check if multipart file is not null
        assertNotNull(contextDtoArgumentCaptor.getValue());

        // Compare the contents
        assertEquals(mockFile, contextDtoArgumentCaptor.getValue().getFile());
    }

    @Test
    void updateFromServer() throws Exception {

        // setup UpdateOrchestrator mock
        when(updateOrchestrator.update(any())).thenReturn(combinedUpdateReport);

        // need to add two query parameters in the request, queryId and token
        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                        .param("sourceType", "SERVER")
                        .param("queryId", "queryId")
                        .param("token", "token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").isNotEmpty());

        // Verify that the method was called
        verify(updateOrchestrator).update(contextDtoArgumentCaptor.capture());

        // Check if queryId and token are not null
        assertNotNull(contextDtoArgumentCaptor.getValue().getQueryId());
        assertNotNull(contextDtoArgumentCaptor.getValue().getToken());
        assertEquals("queryId", contextDtoArgumentCaptor.getValue().getQueryId());
        assertEquals("token", contextDtoArgumentCaptor.getValue().getToken());
    }

    @Test
    void updateFromServerException() throws Exception {
        // Create a mock set of ConstraintViolations
        Set<ConstraintViolation<UpdateContext>> violations = new HashSet<>();
        ConstraintViolation<UpdateContext> violation = mock(ConstraintViolation.class);
        violations.add(violation);

        // Setup the mock to throw the ConstraintViolationException
        doThrow(new ConstraintViolationException(violations)).when(updateContextDtoValidator).validate(any());

        // Invalid request with no token parameter
        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                        .param("sourceType", "SERVER")
                        .param("queryId", "queryId"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("invalid-parameter"));

    }

    @Test
    void updateFromFileException() throws Exception {
        // Create a mock set of ConstraintViolations
        Set<ConstraintViolation<UpdateContext>> violations = new HashSet<>();
        ConstraintViolation<UpdateContext> violation = mock(ConstraintViolation.class);
        violations.add(violation);

        // Setup the mock to throw the ConstraintViolationException
        doThrow(new ConstraintViolationException(violations)).when(updateContextDtoValidator).validate(any());

        // Invalid request with no file parameter
        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                        .param("sourceType", "FILE"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("invalid-parameter"));
    }

    @Test
    void updateTypeMismatchException() throws Exception {
        // Invalid request with no file parameter
        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                        .param("sourceType", "INVALID"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("parameter-type-mismatch"));
    }

    @Test
    void updateWithSampleData() throws Exception {
        // setup UpdateContext
        UpdateContext contextDto = UpdateContext.builder()
                .sourceType(UpdateContext.SourceType.SAMPLEDATA)
                .build();

        // setup UpdateOrchestrator mock
        when(updateOrchestrator.update(contextDto)).thenReturn(combinedUpdateReport);

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                        .param("sourceType", "SAMPLEDATA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trades").exists());
    }
}