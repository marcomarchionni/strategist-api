package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidDataException extends CustomException {
    public static final String message = "Data do not belong to authenticated user";
    public static final String title = "Invalid data";
    public static final HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;

    public InvalidDataException() {
        super(message, title, statusCode);
    }
}
