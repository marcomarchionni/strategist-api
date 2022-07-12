package com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions;

public class UnableToSaveEntityException extends RuntimeException {

    public UnableToSaveEntityException(String message) {
        super(message);
    }
}
