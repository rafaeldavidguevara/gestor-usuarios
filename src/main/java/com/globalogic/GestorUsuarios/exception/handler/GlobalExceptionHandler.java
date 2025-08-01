package com.globalogic.GestorUsuarios.exception.handler;

import com.globalogic.GestorUsuarios.exception.BearerTokenException;
import com.globalogic.GestorUsuarios.exception.EncoderException;
import com.globalogic.GestorUsuarios.exception.UserAuthenticationException;
import com.globalogic.GestorUsuarios.util.response.ErrorElement;
import com.globalogic.GestorUsuarios.util.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorElement errorElement = ErrorElement.builder()
                .timestamp(Timestamp.valueOf(java.time.LocalDateTime.now()))
                .codigo(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getBindingResult().getFieldError().getDefaultMessage())
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(List.of(errorElement))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BearerTokenException.class)
    public ResponseEntity<ErrorResponse> handleBearerTokenException(BearerTokenException ex) {
        ErrorElement errorElement = ErrorElement.builder()
                .timestamp(Timestamp.valueOf(java.time.LocalDateTime.now()))
                .codigo(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(List.of(errorElement))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUserAuthenticationException(UserAuthenticationException ex) {
        ErrorElement errorElement = ErrorElement.builder()
                .timestamp(Timestamp.valueOf(java.time.LocalDateTime.now()))
                .codigo(ex.getStatus().value())
                .detail(ex.getMessage())
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(List.of(errorElement))
                .build();
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(EncoderException.class)
    public ResponseEntity<ErrorResponse> handleEncoderException(EncoderException ex) {
        ErrorElement errorElement = ErrorElement.builder()
                .timestamp(Timestamp.valueOf(java.time.LocalDateTime.now()))
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(List.of(errorElement))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
