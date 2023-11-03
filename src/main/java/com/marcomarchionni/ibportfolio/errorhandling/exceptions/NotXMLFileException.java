package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.net.URI;

public class NotXMLFileException extends IOException implements ErrorResponse {

    public NotXMLFileException() {
        super("Uploaded file is not an xml file or does not have an xml extension");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(getStatusCode(), getMessage());
        pd.setType(URI.create("not-an-xml-file"));
        pd.setTitle("Not an XML file");
        return pd;
    }
}
