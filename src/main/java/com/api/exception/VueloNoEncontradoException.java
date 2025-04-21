package com.api.exception;

public class VueloNoEncontradoException extends RuntimeException {
    public VueloNoEncontradoException(Long id) {
        super("Vuelo no encontrado con el id " + id);
    }
}
