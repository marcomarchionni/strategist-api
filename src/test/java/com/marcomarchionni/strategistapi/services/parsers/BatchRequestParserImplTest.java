package com.marcomarchionni.strategistapi.services.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchRequestParserImplTest {

    @Mock
    HttpServletRequest request;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void parseRequest() throws IOException {
        // Given
        String batchRequest = """
                --batch_3f4e49d5-96cb-4f7b-b2f5-4d26bcca5f6a\r
                Content-Type: multipart/mixed; boundary=changeset_c76c63f7-5ea6-45ce-b8a7-d3d092264da9\r
                \r
                --changeset_c76c63f7-5ea6-45ce-b8a7-d3d092264da9\r
                Content-Type: application/http\r
                Content-Transfer-Encoding: binary\r
                \r
                POST undefined HTTP/1.1\r
                Accept: application/json;odata=light;q=1,application/json;odata=verbose;q=0.5\r
                Content-Id: 0\r
                Content-Type: application/json; charset=utf-8\r
                \r
                {"id":0,"createdAt":"2024-04-29","name":"new portfolio","description":"new desc"}\r
                \r
                --changeset_c76c63f7-5ea6-45ce-b8a7-d3d092264da9--\r
                --batch_3f4e49d5-96cb-4f7b-b2f5-4d26bcca5f6a--\r
                """;
        InputStream batchRequestStream = new ByteArrayInputStream(batchRequest.getBytes(StandardCharsets.UTF_8));
        ServletInputStream servletInputStream = new DelegatingServletInputStream(batchRequestStream);

        String contentType = "multipart/mixed; boundary=batch_3f4e49d5-96cb-4f7b-b2f5-4d26bcca5f6a";
        when(request.getContentType()).thenReturn(contentType);
        when(request.getInputStream()).thenReturn(servletInputStream);
        BatchRequestParser parser = new BatchRequestParserImpl(objectMapper);

        // When
        var operations = parser.parseRequest(request, PortfolioSave.class);

        // Then
        assertEquals(1, operations.size());
        assertEquals("POST", operations.get(0).getMethod());
        assertNull(operations.get(0).getEntityId());
        assertEquals("new portfolio", operations.get(0).getDto().getName());
    }
}