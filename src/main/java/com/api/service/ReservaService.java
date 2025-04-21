package com.api.service;

import com.api.dto.ReservaActualizarRequestDTO;
import com.api.dto.ReservaRequestDTO;
import com.api.dto.ReservaResponseDTO;


import java.util.List;

public interface ReservaService {

    List<ReservaResponseDTO> listarReservas();

    List<ReservaResponseDTO> obtenerReservasPorUsuario(Long usuarioId);

    ReservaResponseDTO obtenerReservaPorId(Long id);

    ReservaResponseDTO crearReserva(ReservaRequestDTO reservaRequestDTO);

    ReservaResponseDTO actualizarEstadoReserva(Long id, ReservaActualizarRequestDTO actualizarRequestDTO);

    void eliminarReserva(Long id);

}
