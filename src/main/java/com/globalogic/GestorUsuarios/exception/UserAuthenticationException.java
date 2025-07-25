package com.globalogic.GestorUsuarios.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAuthenticationException extends RuntimeException{
    private final HttpStatus status;

    public UserAuthenticationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
