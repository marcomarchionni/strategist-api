package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            UnableToSaveEntitiesException.class,
            UnableToDeleteEntitiesException.class,
            EmptyFileException.class,
            NotXMLFileException.class,
            IbServerErrorException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(ErrorResponse ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
    }


//    @ExceptionHandler({
////            MethodArgumentTypeMismatchException.class,
//            IllegalArgumentException.class,
////            ConstraintViolationException.class,
//
//    })
//    public ResponseEntity<Object> handleBadRequestException(ErrorResponse ex) {
//        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
//    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders
//    headers, HttpStatusCode status, WebRequest request) {
//        return createErrorResponse("http-message-body-not-readable", ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail body = ex.getBody();
        body.setType(URI.create("invalid-query-parameter"));
        body.setTitle("Invalid query parameter(s)");
        String detail = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(" - "));
        if (!detail.isEmpty()) {
            body.setDetail(detail);
        }
        return handleExceptionInternal(ex, body, headers, status, request);
    }


    /*TODO: override BindException*/
//    @Override
//    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode
//    status, WebRequest request) {
//        return handleCustomBindException(ex);
//    }

//    private ResponseEntity<Object> handleCustomBindException(BindException ex) {
//        String detail = ex
//                .getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .findFirst()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .orElse(ex.getMessage());
//        return createErrorResponse("argument-not-valid", detail, HttpStatus.BAD_REQUEST);
//    }
}
