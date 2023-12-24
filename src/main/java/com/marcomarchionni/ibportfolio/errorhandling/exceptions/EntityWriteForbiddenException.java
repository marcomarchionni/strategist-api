package com.marcomarchionni.ibportfolio.errorhandling.exceptions;

import org.springframework.http.HttpStatus;

public abstract class EntityWriteForbiddenException extends CustomException {

    public EntityWriteForbiddenException(String message, String title) {
        super(message, title, HttpStatus.FORBIDDEN);
    }
}
