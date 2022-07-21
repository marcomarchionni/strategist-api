package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class FailingQueryException extends RuntimeException {

    public FailingQueryException(String message) {
        super(message);
    }
}
