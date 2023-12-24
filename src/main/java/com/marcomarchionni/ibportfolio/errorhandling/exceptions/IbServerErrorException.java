package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IbServerErrorException extends CustomException {

    public IbServerErrorException(ResponseEntity<?> response) {
        this(getMessage(response));
    }

    public IbServerErrorException(String message) {
        super(message, "IB server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getMessage(ResponseEntity<?> response) {
        String message;
        if (response.getStatusCode() != HttpStatus.OK) {
            message = "IB server responded with status code " + response.getStatusCode();
        } else if (response.getBody() == null) {
            message = "IB server responded with an empty body";
        } else {
            message = "IB server error";
        }
        return message;
    }
}
