package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.update.FileUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UpdateControllerTest {

    @Mock
    FileUpdater fileUpdater;

    @InjectMocks
    UpdateController updateController;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(updateController).build();
    }

    @Test
    void updateFromFile() throws Exception {

        doNothing().when(fileUpdater).update((File) any());

        mockMvc.perform(get("/update/file"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")))
                .andExpect(content().string("Update from file completed"));
    }

    @Test
    void updateFromServer() {
    }
}