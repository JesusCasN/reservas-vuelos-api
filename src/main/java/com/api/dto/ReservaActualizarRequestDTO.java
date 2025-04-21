package com.api.dto;

import com.api.model.EstadoReserva;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservaActualizarRequestDTO implements Serializable {

    @NotNull(message = "El estado es obligatorio")
    private EstadoReserva estado;

}
