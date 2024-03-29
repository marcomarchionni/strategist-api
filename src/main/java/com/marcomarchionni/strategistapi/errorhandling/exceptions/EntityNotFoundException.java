package com.marcomarchionni.strategistapi.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class EntityNotFoundException extends CustomException {
    private static final String DEFAULT_TITLE = "Entity Not Found";
    private static final HttpStatusCode DEFAULT_STATUS_CODE = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(Class<?> entityClass, Long entityId) {
        super(buildMessage(entityClass, entityId), DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }

    public EntityNotFoundException(String email) {
        super("User with email " + email + "not found", DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }

    private static String buildMessage(Class<?> entityClass, Long entityId) {
        return String.format("Entity %s with id %d not found", entityClass.getSimpleName(), entityId);
    }
}
