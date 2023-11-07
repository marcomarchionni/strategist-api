package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

public class UnableToSaveEntitiesException extends EntityWriteForbiddenException {

    public UnableToSaveEntitiesException(String message) {
        super(message, "Unable to save entities");
    }
}
