package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class UserNotAuthenticatedException extends CustomException {

    private static final String message = "Cannot retrieve authenticated user. Please login.";
    private static final String title = "User Not Authenticated";
    private static final HttpStatusCode statusCode = HttpStatus.UNAUTHORIZED;

    public UserNotAuthenticatedException() {
        super(message, title, statusCode);
    }
}

