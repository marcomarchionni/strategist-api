package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class FilterParsingException extends CustomException {
    private static final String message = "Invalid filter parameter";
    private static final String title = "Invalid filter parameter";
    private static final HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;

    @SuppressWarnings("unused")
    public FilterParsingException() {
        super(message, title, statusCode);
    }

    public FilterParsingException(String message) {
        super(message, title, statusCode);
    }
}
