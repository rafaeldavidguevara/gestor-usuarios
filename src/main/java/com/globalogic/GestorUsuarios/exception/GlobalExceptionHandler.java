package com.globalogic.GestorUsuarios.exception;

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

}
