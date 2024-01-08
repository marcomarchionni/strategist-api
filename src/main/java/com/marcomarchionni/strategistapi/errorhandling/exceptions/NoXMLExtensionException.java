package com.marcomarchionni.strategistapi.errorhandling.exceptions;

public class NoXMLExtensionException extends InvalidXMLFileException {

    public NoXMLExtensionException() {
        super("Uploaded file has no XML extension", "No XML extension");
    }
}
