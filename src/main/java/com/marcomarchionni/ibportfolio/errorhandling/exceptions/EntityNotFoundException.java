package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class EntityNotFoundException extends CustomException {
    public static final String DEFAULT_TITLE = "Entity Not Found";
    public static final HttpStatusCode DEFAULT_STATUS_CODE = HttpStatus.NOT_FOUND;
    public EntityNotFoundException(String message) {
        super(message, DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }

    public EntityNotFoundException(Class<?> entityClass, Long entityId, String userAccountId) {
        super(buildMessage(entityClass, entityId, userAccountId), DEFAULT_TITLE, DEFAULT_STATUS_CODE);
    }

    private static String buildMessage(Class<?> entityClass, Long entityId, String userAccountId) {
        return String.format("Entity %s with id %d not found for account: %s", entityClass.getSimpleName(), entityId,
                userAccountId);
    }
}
