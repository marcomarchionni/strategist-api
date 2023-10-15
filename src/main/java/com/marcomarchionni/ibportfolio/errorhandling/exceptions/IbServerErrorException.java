package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class IbServerErrorException extends RuntimeException {

    public IbServerErrorException() {
        super("Error while invoking external parsing");
    }

}
