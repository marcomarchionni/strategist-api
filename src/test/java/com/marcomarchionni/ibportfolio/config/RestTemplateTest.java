package com.marcomarchionni.ibportfolio.config;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestTemplateTest {

    @Value("${flexservice.token}")
    private String token;

    @Value("${flexservice.query-id}")
    private String queryId;

    @Value("${flexservice.auth-url}")
    private String authUrl;

    @Autowired
    RestTemplate restTemplate;

    @Test
    @Disabled
    void getToEntity() {
        // Set headers as documented by Interactive Brokers
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Java/17");
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Call to server, response mapped in FlexStatementResponseDto
        ResponseEntity<FlexStatementResponseDto> response = restTemplate.exchange(
                authUrl, HttpMethod.GET, request, FlexStatementResponseDto.class, token, queryId);

        FlexStatementResponseDto flexStatementResponseDto = response.getBody();
        assertNotNull(flexStatementResponseDto);
        assertEquals("Success", flexStatementResponseDto.getStatus());
    }

    @Test
    @Disabled
    void getToString() {
        // Set headers as documented by Interactive Brokers
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Java/17");
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Call to server, response mapped in FlexStatementResponseDto
        ResponseEntity<String> response = restTemplate.exchange(
                authUrl, HttpMethod.GET, request, String.class, token, queryId);

        String rawXml = response.getBody();
        assertNotNull(rawXml);
        assertTrue(rawXml.contains("Success"));
    }
}