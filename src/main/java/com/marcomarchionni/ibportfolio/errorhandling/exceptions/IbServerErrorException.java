package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

import java.net.URI;

public class IbServerErrorException extends RuntimeException implements ErrorResponse {

    public IbServerErrorException(ResponseEntity<?> response) {
        super(getMessage(response));
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

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(getStatusCode(), getMessage());
        pd.setType(URI.create("ib-server-error"));
        pd.setTitle("IB server error");
        return pd;
    }
}
