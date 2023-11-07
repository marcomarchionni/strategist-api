package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.net.URI;

public class InvalidXMLFileException extends RuntimeException implements ErrorResponse {

    private final String title;

    public InvalidXMLFileException() {
        this("XML file does not match the expected format");
    }

    public InvalidXMLFileException(Exception e) {
        super("XML file does not match the expected format", e);
        this.title = "Invalid XML file";
    }

    public InvalidXMLFileException(String message) {
        this(message, "Invalid XML file");
    }

    public InvalidXMLFileException(String message, String title) {
        super(message);
        this.title = title;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
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
