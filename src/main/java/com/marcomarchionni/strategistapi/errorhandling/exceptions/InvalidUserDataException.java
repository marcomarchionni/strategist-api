package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidUserDataException extends CustomException {
    private static final String message = "Data do not belong to authenticated user";
    private static final String title = "Invalid data";
    private static final HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;

    public InvalidUserDataException() {
        super(message, title, statusCode);
    }
}
