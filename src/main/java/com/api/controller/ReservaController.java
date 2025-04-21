package com.api.controller;

import com.api.dto.ReservaActualizarRequestDTO;
import com.api.dto.ReservaRequestDTO;
import com.api.dto.ReservaResponseDTO;
import com.api.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones para gestionar reservas de vuelos")
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Endpoint para listar todas las reservas (Solo ADMIN)
     * Respuesta JSON:
     * {
     * "id": 5,
     * "usuarioId": 9,
     * "vueloId": 4,
     * "estado": "PENDIENTE",
     * "fechaReserva": "2025-03-06T16:55:05"
     * }
     */
    @Operation(summary = "Listar todas las reservas (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        List<ReservaResponseDTO> reservas = reservaService.listarReservas();
        return reservas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para obtener reservas de un usuario (Solo el usuario o ADMIN)
     * Recibe respuestas JSON
     * Ejemplo
     * {
     * "id": 10,
     * "usuarioId": 14,
     * "vueloId": 4,
     * "estado": "PENDIENTE",
     * "fechaReserva": "2025-03-27T17:18:10"
     * }
     */
    @Operation(summary = "Obtener reservas de un usuario", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #usuarioId == authentication.principal.id)")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasPorUsuario(usuarioId);
        return reservas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para obtener una reserva por ID (Solo el usuario o ADMIN)
     * Recibe respuesta JSON
     * Ejemplo
     * {
     * "id": 10,
     * "usuarioId": 14,
     * "vueloId": 4,
     * "estado": "PENDIENTE",
     * "fechaReserva": "2025-03-27T17:18:10"
     * }
     */
    @Operation(summary = "Obtener una reserva por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    }

    /**
     * Endpoint para Crear una reserva (Solo CLIENTE)
     * Recibe un JSON con id usuario y id del vuelo, y devuelve la creacion de una reserva con el estado "PENDIENTE".
     * Ejemplo de petición:
     * {
     * "email":  "usuarioId": 9,
     * "vueloId": 4
     * }
     * Respuesta:
     * {
     * "id": x,
     * "usuarioId": 9,
     * "vueloId": 4,
     * "estado": "PENDIENTE",
     * "fechaReserva": "2025-03-27T17:08:36.356869"
     * }
     */
    @Operation(summary = "Crear una nueva reserva (Solo CLIENTE)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaRequestDTO reservaRequestDTO) {
        ReservaResponseDTO nuevaReserva = reservaService.crearReserva(reservaRequestDTO);
        return ResponseEntity
                .created(URI.create("/reservas/" + nuevaReserva.getId()))
                .body(nuevaReserva);
    }

    /**
     * Endpoint para actualizar reserva a otro estado como por ejemplo "CANCELADA" (Solo ADMIN)
     * Recibe un JSON con el estado de la reserva.
     * Ejemplo de petición:
     * {
     * "estado": "CANCELADA"
     * }
     * Respuesta:
     * {
     * "id": 8,
     * "usuarioId": 14,
     * "vueloId": 4,
     * "estado": "CANCELADA",
     * "fechaReserva": "2025-03-06T22:07:12"
     * }
     */
    @Operation(summary = "Actualizar estado de una reserva (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarEstadoReserva(@PathVariable Long id,
                                                                      @Valid @RequestBody ReservaActualizarRequestDTO requestDTO) {
        return ResponseEntity.ok(reservaService.actualizarEstadoReserva(id, requestDTO));
    }

    /**
     * Endpoint para eliminar reservas (Solo ADMIN)
     * Respuesta:
     * 204 No Content
     */
    @Operation(summary = "Eliminar una reserva (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
