package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.net.URI;

public abstract class CustomException extends RuntimeException implements ErrorResponse {
    private final String message;
    private final String title;
    private final HttpStatusCode statusCode;

    public CustomException(String message, String title, HttpStatusCode statusCode) {
        super(message);
        this.message = message;
        this.title = title;
        this.statusCode = statusCode;
    }

    public CustomException(String message, String title, HttpStatusCode statusCode, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.title = title;
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public ProblemDetail getBody() {
        Throwable cause = getCause();
        String details = (cause == null) ? message : (message + ". Cause: " + cause.getMessage());
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(statusCode, details);
        pd.setType(getType(title));
        pd.setTitle(title);
        return pd;
    }

    private URI getType(String title) {
        return URI.create(title.toLowerCase().replace(" ", "-"));
    }
}
