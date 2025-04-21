package com.api.exception;

public class RolNoEncontradoException extends RuntimeException {
    public RolNoEncontradoException(String rol) {
        super("Rol no encontrado con nombre: " + rol);
    }
}


