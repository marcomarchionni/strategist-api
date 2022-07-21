package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
