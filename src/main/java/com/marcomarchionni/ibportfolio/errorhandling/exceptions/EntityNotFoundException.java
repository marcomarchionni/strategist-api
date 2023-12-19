package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class EntityNotFoundException extends CustomException {

    public static final String DEFAULT_MESSAGE = "Entity not found";
    public static final String DEFAULT_TITLE = "Entity Not Found";
    public static final HttpStatusCode DEFAULT_STATUS_CODE = HttpStatus.NOT_FOUND;

    //TODO: add a constructor that takes an entity name and user account id
    @SuppressWarnings("unused")
    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }

    public EntityNotFoundException(String message) {
        super(message, DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }
}
