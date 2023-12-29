package com.marcomarchionni.ibportfolio.errorhandling;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
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
            InvalidXMLFileException.class,
            NoXMLExtensionException.class,
            UserAuthenticationException.class,
            IbServerErrorException.class}
    )
    public ResponseEntity<Object> handleCustomExceptions(ErrorResponse ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.PAYLOAD_TOO_LARGE, ex.getMessage());
        pd.setType(URI.create("max-upload-size-exceeded"));
        pd.setTitle("Max upload size exceeded");
        if (ex.getCause() instanceof IllegalStateException illegalStateException) {
            pd.setDetail(illegalStateException.getMessage());
        }
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        if (ex.getCause() instanceof ConstraintViolationException constraintViolationEx) {
            String constraintName = constraintViolationEx.getConstraintName();
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, constraintName);
            pd.setType(URI.create("data-integrity-violation"));
            pd.setTitle("Data integrity violation");
            return ResponseEntity.status(pd.getStatus()).body(pd);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getRootCause());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        pd.setType(URI.create("access-denied"));
        pd.setTitle("Access denied");
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleUnauthorized(AuthenticationException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        pd.setType(URI.create("unauthorized"));
        pd.setTitle("Unauthorized");
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }


    @Override
    @Nullable
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setType(URI.create("endpoint-not-found"));
        pd.setTitle("Endpoint not found");
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

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

    @Nullable
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail body = createProblemDetail(ex, status, "Failed to read request. Request body is null or " +
                "invalid", null, null, request);
        body.setTitle("Request body not readable");
        body.setType(URI.create("request-body-not-readable"));
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
