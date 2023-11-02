package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import java.io.IOException;

public class NotXMLFileException extends IOException {

    public NotXMLFileException() {
        super("Uploaded file is not an xml file");
    }
}
