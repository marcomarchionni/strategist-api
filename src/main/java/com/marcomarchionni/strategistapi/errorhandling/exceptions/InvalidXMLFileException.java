package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidXMLFileException extends CustomException {

    private static final String defaultMessage = "XML file does not match the expected format";
    private static final String defaultTitle = "Invalid XML file";
    private static final HttpStatusCode defaultStatusCode = HttpStatus.BAD_REQUEST;

    public InvalidXMLFileException(String message, String title) {
        super(message, title, defaultStatusCode);
    }

    public InvalidXMLFileException(Exception e) {
        super(defaultMessage + ". Cause: " + e.getMessage(), defaultTitle, defaultStatusCode, e);
    }

    public InvalidXMLFileException() {
        this(defaultMessage, defaultTitle);
    }

}
