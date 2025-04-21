package com.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservaRequestDTO implements Serializable {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del vuelo es obligatorio")
    private Long vueloId;

}
