package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.net.URI;

public abstract class EntityWriteForbiddenException extends RuntimeException implements ErrorResponse {

    private final String title;

    public EntityWriteForbiddenException(String message, String title) {
        super(message);
        this.title = title;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(getStatusCode(), getMessage());
        pd.setType(getType(title));
        pd.setTitle(title);
        return pd;
    }

    private URI getType(String title) {
        return URI.create(title.toLowerCase().replace(" ", "-"));
    }

}
