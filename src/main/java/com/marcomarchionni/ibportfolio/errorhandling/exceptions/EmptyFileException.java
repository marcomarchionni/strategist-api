package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import java.io.IOException;

public class EmptyFileException extends IOException {

    public EmptyFileException() {
        super("Uploaded file is empty or invalid");
    }
}
