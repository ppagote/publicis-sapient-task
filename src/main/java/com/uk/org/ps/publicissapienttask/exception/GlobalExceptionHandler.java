package com.uk.org.ps.publicissapienttask.exception;

import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import io.swagger.annotations.Api;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Api(tags = {"Errors"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> globleExcpetionHandler(Exception ex, WebRequest request) {
       /* ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);*/
        return handleEveryException(ex, request);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleCardAlreadyExistsException(CardAlreadyExistsException ex, WebRequest request) {
        ErrorDetails exceptionResponse = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.FORBIDDEN.name());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex, WebRequest request) {
        ErrorDetails exceptionResponse = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.FORBIDDEN.name());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCardException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidCardException(InvalidCardException ex, WebRequest request) {
        ErrorDetails exceptionResponse = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.NOT_ACCEPTABLE.name());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errors, status.name());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("unchecked")
    protected @Override
    ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                   HttpStatus status, WebRequest request) {

        ResponseEntity<?> responseEntity;

        if (!status.isError()) {
            responseEntity = handleStatusException(ex, status, request);
        } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
            responseEntity = handleEveryException(ex, request);
        } else {
            responseEntity = handleEveryException(ex, request);
        }

        return (ResponseEntity<Object>) responseEntity;
    }

    protected ResponseEntity<ErrorDetails> handleStatusException(Exception ex, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), status.name());
        return new ResponseEntity<>(errorDetails, status);

        /*return RestResponse.builder()
                .status(status)
                .message("Execution halted")
                .path(getPath(request))
                .entity();*/
    }

    protected ResponseEntity<ErrorDetails> handleEveryException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

       /* return RestResponse.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("Server encountered an error")
                .path(getPath(request))
                .entity();*/
    }
}
