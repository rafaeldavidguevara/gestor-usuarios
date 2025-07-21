package com.globalogic.GestorUsuarios.exception;

public class BearerTokenException extends RuntimeException{
    public BearerTokenException(String message) {
        super(message);
    }
}
