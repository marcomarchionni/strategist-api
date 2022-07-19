package com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions;

public class FailingQueryException extends RuntimeException {

    public FailingQueryException(String message) {
        super(message);
    }
}
