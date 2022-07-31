package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exc) {
        return getResponseEntityWithErrorResponse(exc.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnableToDeleteEntitiesException.class, UnableToSaveEntitiesException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception exc) {
        return getResponseEntityWithErrorResponse(exc.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exc) {
        return getResponseEntityWithErrorResponse(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntityWithErrorObject(ex.getMessage(), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntityWithErrorObject(ex.getMessage(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntityWithErrorObject(ex.getMessage(), status);
    }

    private ResponseEntity<ErrorResponse> getResponseEntityWithErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, httpStatus);
    }

    private ResponseEntity<Object> getResponseEntityWithErrorObject(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, httpStatus);
    }
}
