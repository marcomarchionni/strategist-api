package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return createErrorResponse("bad-request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders
//    headers, HttpStatusCode status, WebRequest request) {
//        return createErrorResponse("http-message-body-not-readable", ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
//    headers, HttpStatusCode status, WebRequest request) {
//        return handleCustomBindException(ex);
//    }
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
        return ResponseEntity.status(httpStatus).headers(httpHeaders).body(details);
    }
}
