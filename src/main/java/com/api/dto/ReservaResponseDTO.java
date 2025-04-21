package com.api.dto;

import com.api.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservaResponseDTO implements Serializable {
    private Long id;
    private Long usuarioId;
    private Long vueloId;
    private EstadoReserva estado = EstadoReserva.PENDIENTE;
    private LocalDateTime fechaReserva = LocalDateTime.now();
}
