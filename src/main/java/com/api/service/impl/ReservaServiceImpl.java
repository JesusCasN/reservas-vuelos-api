package com.api.service.impl;

import com.api.dto.ReservaActualizarRequestDTO;
import com.api.dto.ReservaRequestDTO;
import com.api.dto.ReservaResponseDTO;
import com.api.exception.CambioEstadoInvalidoException;
import com.api.exception.ReservaNoEncontradaException;
import com.api.exception.UsuarioNoEncontradoException;
import com.api.exception.VueloNoEncontradoException;
import com.api.mapper.ReservaMapper;
import com.api.model.EstadoReserva;
import com.api.model.Reserva;
import com.api.model.Usuario;
import com.api.model.Vuelo;
import com.api.repository.ReservaRepository;
import com.api.repository.UsuarioRepository;
import com.api.repository.VueloRepository;
import com.api.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VueloRepository vueloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarReservas() {
        return reservaRepository.findAllConUsuarioYVuelo().stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> obtenerReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioIdConUsuarioYVuelo(usuarioId).stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaResponseDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findByIdConUsuarioYVuelo(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));
        return ReservaMapper.toDTO(reserva);
    }

    @Override
    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO reservaRequestDTO) {

        Usuario usuario = usuarioRepository.findById(reservaRequestDTO.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(reservaRequestDTO.getUsuarioId()));

        Vuelo vuelo = vueloRepository.findById(reservaRequestDTO.getVueloId())
                .orElseThrow(() -> new VueloNoEncontradoException(reservaRequestDTO.getVueloId()));

        //validar si hay asientos disponibles
        if (vuelo.getAsientosDisponibles() <= 0) {
            throw new VueloNoEncontradoException(vuelo.getId());
        }

        // Reducir el número de asientos disponibles en el vuelo
        vuelo.setAsientosDisponibles(vuelo.getAsientosDisponibles() - 1);

        Reserva reserva = ReservaMapper.toEntity(reservaRequestDTO, usuario, vuelo);
        reserva.setUsuario(usuario);
        reserva.setVuelo(vuelo);

        //guardar usuario en la base de datos
        Reserva nuevaReserva = reservaRepository.save(reserva);

        return ReservaMapper.toDTO(nuevaReserva);
    }

    @Override
    @Transactional
    public ReservaResponseDTO actualizarEstadoReserva(Long id, ReservaActualizarRequestDTO actualizarRequestDTO) {
        // Buscar la reserva en la base de datos
        Reserva reserva = reservaRepository.findByIdConUsuarioYVuelo(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Validar que la reserva tiene un estado válido antes de cambiarlo
        EstadoReserva estadoActual = reserva.getEstado();
        EstadoReserva nuevoEstado = actualizarRequestDTO.getEstado();

        // **Reglas de negocio**: Controlar cambios de estado inválidos
        if (estadoActual == EstadoReserva.CONFIRMADA && nuevoEstado == EstadoReserva.PENDIENTE) {
            throw new CambioEstadoInvalidoException("No se puede revertir una reserva confirmada a pendiente.");
        }

        if (estadoActual == EstadoReserva.CANCELADA) {
            throw new CambioEstadoInvalidoException("No se puede modificar una reserva cancelada.");
        }

        // Actualizar el estado de la reserva
        ReservaMapper.actualizarEstado(reserva, actualizarRequestDTO);

        // Guardar la reserva actualizada en la base de datos
        Reserva reservaActualizada = reservaRepository.save(reserva);

        return ReservaMapper.toDTO(reservaActualizada);
    }


    @Override
    @Transactional
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        reservaRepository.delete(reserva);

    }
}
