package com.api.exception;

public class ContrasenaIncorrectaException extends RuntimeException {
    public ContrasenaIncorrectaException(String message) {
        super(message);
    }
}
