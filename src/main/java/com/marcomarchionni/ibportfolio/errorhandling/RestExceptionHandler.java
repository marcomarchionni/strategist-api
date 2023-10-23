package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UploadedFileException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return createErrorResponse("entity-not-found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToDeleteEntitiesException.class)
    public ResponseEntity<Object> handleUnableToDeleteException(Exception ex) {
        return createErrorResponse("unable-to-delete-entities", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnableToSaveEntitiesException.class)
    public ResponseEntity<Object> handleUnableToSaveException(Exception ex) {
        return createErrorResponse("unable-to-save-entities", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class,
            UploadedFileException.class,
            MaxUploadSizeExceededException.class

    })
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return createErrorResponse("bad-request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return createErrorResponse("http-message-body-not-readable", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleCustomBindException(ex);
    }
    /*TODO: override BindException*/
//    @Override
//    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return handleCustomBindException(ex);
//    }

    private ResponseEntity<Object> handleCustomBindException(BindException ex) {
        String detail = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        return createErrorResponse("argument-not-valid", detail, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> createErrorResponse(String type, String detail, HttpStatus httpStatus) {
        String title = StringUtils.capitalize(type.replace("-", " "));
        ProblemDetails details = ProblemDetails
                .builder()
                .type(type)
                .title(title)
                .status(httpStatus.value())
                .detail(detail)
                .timeStamp(System.currentTimeMillis())
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(details, httpHeaders, httpStatus);
    }
}
