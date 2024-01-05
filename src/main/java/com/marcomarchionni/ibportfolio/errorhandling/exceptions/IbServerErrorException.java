package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IbServerErrorException extends CustomException {

    public IbServerErrorException(ResponseEntity<?> response, Class<?> clazz) {
        this(getMessage(response, clazz));
    }

    public IbServerErrorException(String message) {
        super(message, "IB server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getMessage(ResponseEntity<?> response, Class<?> clazz) {
        String baseMessage = "Error while fetching " + clazz.getSimpleName() + ". ";
        String statusCodeMessage = "IB server responded with status code " + response.getStatusCode() + ". ";
        String bodyMessage = response.getBody() == null ? "IB server responded with an empty body" : "Response body: "
                + response.getBody();
        return baseMessage + statusCodeMessage + bodyMessage;
    }
}
