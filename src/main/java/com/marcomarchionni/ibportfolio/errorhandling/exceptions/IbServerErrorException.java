package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class IbServerErrorException extends RuntimeException {

    public IbServerErrorException(String message) {
        super(message);
    }
}