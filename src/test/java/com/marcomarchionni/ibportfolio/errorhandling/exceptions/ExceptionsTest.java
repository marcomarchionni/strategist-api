package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionsTest {

    @Test
    void InvalidXMLFileException() {

        InvalidXMLFileException exception = new InvalidXMLFileException();

        assertEquals("XML file does not match the expected format", exception.getMessage());
        assertNotNull(exception.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid XML file", exception.getBody().getTitle());
        assertEquals("invalid-xml-file", exception.getBody().getType().toString());
    }

    @Test
    void UnableToSaveEntitiesException() {

        UnableToSaveEntitiesException exception = new UnableToSaveEntitiesException("Error message");

        assertEquals("Error message", exception.getMessage());
        assertNotNull(exception.getBody());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Unable to save entities", exception.getBody().getTitle());
        assertEquals("unable-to-save-entities", exception.getBody().getType().toString());
    }

    @Test
    void IbServerErrorException() {

        IbServerErrorException exception = new IbServerErrorException("Error message");

        assertEquals("Error message", exception.getMessage());
        assertNotNull(exception.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("IB server error", exception.getBody().getTitle());
        assertEquals("ib-server-error", exception.getBody().getType().toString());
    }
}