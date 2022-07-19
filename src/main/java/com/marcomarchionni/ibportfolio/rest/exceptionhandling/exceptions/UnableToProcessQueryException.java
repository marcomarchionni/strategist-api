package com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions;

public class UnableToProcessQueryException extends RuntimeException {

    public UnableToProcessQueryException(String message) {
        super(message);
    }
}
