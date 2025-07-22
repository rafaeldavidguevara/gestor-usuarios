package com.globalogic.GestorUsuarios.exception;

public class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException(String message) {
        super(message);
    }
}
