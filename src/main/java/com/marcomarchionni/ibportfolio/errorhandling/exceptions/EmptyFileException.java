package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.net.URI;

public class EmptyFileException extends IOException implements ErrorResponse {

    public EmptyFileException() {
        super("Uploaded file is empty or invalid");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(getStatusCode(), getMessage());
        pd.setType(URI.create("file-empty-or-invalid"));
        pd.setTitle("File empty or invalid");
        return pd;
    }
}
