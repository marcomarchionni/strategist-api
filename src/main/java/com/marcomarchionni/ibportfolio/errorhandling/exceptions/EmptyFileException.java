package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class EmptyFileException extends InvalidXMLFileException {

    public EmptyFileException() {
        super("Uploaded file is empty", "XML file is empty");
    }

}
