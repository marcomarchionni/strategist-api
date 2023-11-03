package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.net.URI;

public class UnableToSaveEntitiesException extends RuntimeException implements ErrorResponse {

    public UnableToSaveEntitiesException(String message) {
        super(message);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, getMessage());
        pd.setType(URI.create("unable-to-save-entities"));
        pd.setTitle("Unable to save entities");
        return pd;
    }
}
