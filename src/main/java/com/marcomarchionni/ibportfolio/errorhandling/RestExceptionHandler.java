package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exc) {
        return getResponseEntity(exc.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnableToDeleteEntitiesException.class, UnableToSaveEntitiesException.class})
    public ResponseEntity<Object> handleConstraintViolationException(Exception exc) {
        return getResponseEntity(exc.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleBadRequestException(Exception exc) {
        return getResponseEntity(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage().isBlank() ? "Invalid query parameters" : ex.getMessage();
        return getResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(error, httpHeaders, httpStatus);
    }
}
