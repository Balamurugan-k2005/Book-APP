package com.example.BookApp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handelBootNotFoundException(BookNotFoundException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ApiError> handelIdNotFoundException(IdNotFoundException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Media Type Not Supported",
                ex.getMessage(),
                path
    );
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Method Not Supported",
                ex.getMessage(),
                path
        );
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Path Variable is Missing ",
                ex.getMessage(),
                path
        );
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Request Param is Missing",
                ex.getMessage(),
                path
        );
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Type Mismatched",
                ex.getMessage(),
                path
        );
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                "Request Body is not Readable",
                ex.getMessage(),
                path
        );
        return new ResponseEntity<>(error, status);
    }

}
