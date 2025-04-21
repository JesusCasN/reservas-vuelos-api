package com.api.exception;

public class EmailNoEncontradoException extends RuntimeException {
    public EmailNoEncontradoException(String email) {
        super("Email no encontrado: " + email);
    }
}
