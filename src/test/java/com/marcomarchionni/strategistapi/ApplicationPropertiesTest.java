package com.marcomarchionni.strategistapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class ApplicationPropertiesTest {

    @Value("${flexservice.token}")
    private String token;

    @Value("${flexservice.query-id}")
    private String queryId;

    @Value("${token.signing.key}")
    private String tokenSigningKey;

    @Value("${signup.adminKey}")
    private String adminKey;

    @Test
    void testPrivateProperties() {
        assertNotEquals("SET_IN_PRIVATE_PROPERTIES", token);
        assertNotEquals("SET_IN_PRIVATE_PROPERTIES", queryId);
        assertNotEquals("SET_IN_PRIVATE_PROPERTIES", tokenSigningKey);
        assertNotEquals("SET_IN_PRIVATE_PROPERTIES", adminKey);
    }
}
