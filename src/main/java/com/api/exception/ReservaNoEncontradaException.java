package com.api.exception;

public class ReservaNoEncontradaException extends RuntimeException {
    public ReservaNoEncontradaException(Long id) {
        super("Reserva no encontrada con el id " + id);
    }
}
