package com.api.mapper;

import com.api.dto.ReservaActualizarRequestDTO;
import com.api.dto.ReservaRequestDTO;
import com.api.dto.ReservaResponseDTO;
import com.api.model.EstadoReserva;
import com.api.model.Reserva;
import com.api.model.Usuario;
import com.api.model.Vuelo;

import java.time.LocalDateTime;

public class ReservaMapper {

    // Convertir entidad a DTO de respuesta
    public static ReservaResponseDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getUsuario().getId(),
                reserva.getVuelo().getId(),
                reserva.getEstado(),
                reserva.getFechaReserva()
        );
    }

    // Convertir DTO de creación a entidad
    public static Reserva toEntity(ReservaRequestDTO requestDTO, Usuario usuario, Vuelo vuelo) {
        if (requestDTO == null || usuario == null || vuelo == null) {
            return null;
        }
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setVuelo(vuelo);
        reserva.setEstado(EstadoReserva.PENDIENTE); // estado inicial
        reserva.setFechaReserva(LocalDateTime.now()); // fecha automática
        return reserva;
    }

    // Actualizar solo el estado en la entidad (desde DTO de estado)
    public static void actualizarEstado(Reserva reserva, ReservaActualizarRequestDTO estadoDTO) {
        if (reserva != null && estadoDTO != null) {
            reserva.setEstado(estadoDTO.getEstado());
        }
    }
}
