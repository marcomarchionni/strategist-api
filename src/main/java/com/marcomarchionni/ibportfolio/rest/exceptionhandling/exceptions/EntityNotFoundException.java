package com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
