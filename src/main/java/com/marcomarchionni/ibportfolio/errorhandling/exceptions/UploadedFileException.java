package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import java.io.IOException;

public class UploadedFileException extends IOException {

    public UploadedFileException() {
        super("Uploaded file is empty or invalid");
    }

    public UploadedFileException(String message) {
        super(message);
    }
}
