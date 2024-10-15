package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidTokenException extends CustomException {

    private static final String message = "Token is invalid or expired";
    private static final String title = "Invalid Token";
    private static final HttpStatusCode statusCode = HttpStatus.UNAUTHORIZED;

    @SuppressWarnings("unused")
    public InvalidTokenException() {
        super(message, title, statusCode);
    }

    public InvalidTokenException(String message) {
        super(message, title, statusCode);
    }
}
