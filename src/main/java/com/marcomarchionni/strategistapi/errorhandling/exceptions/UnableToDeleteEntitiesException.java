package com.marcomarchionni.strategistapi.errorhandling.exceptions;

public class UnableToDeleteEntitiesException extends EntityWriteForbiddenException {

    public UnableToDeleteEntitiesException(String message) {
        super(message, "Unable to delete entities");
    }
}
