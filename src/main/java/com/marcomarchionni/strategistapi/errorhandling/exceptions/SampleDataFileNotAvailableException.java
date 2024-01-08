package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class SampleDataFileNotAvailableException extends CustomException {

    private static final String message = "Sample data file is not available";
    private static final String title = "Sample data not available";
    private static final HttpStatusCode statusCode = HttpStatus.SERVICE_UNAVAILABLE;

    public SampleDataFileNotAvailableException(Exception e) {
        super(message, title, statusCode, e);
    }
}
