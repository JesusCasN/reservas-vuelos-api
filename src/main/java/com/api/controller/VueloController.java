package com.api.controller;

import com.api.dto.VueloDTO;
import com.api.service.VueloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vuelos")
@RequiredArgsConstructor
@Tag(name = "Vuelos", description = "Operaciones para gestionar vuelos")
public class VueloController {

    private final VueloService vueloService;

    /**
     * Endpoint para listar todos los vuelos
     * Respuestas ejemplo:
     * {
     * "id": 5,
     * "aerolinea": "Aerolinea A",
     * "origen": "Mexico",
     * "destino": "Chiapas",
     * "fechaSalida": "2025-03-07T03:37:50",
     * "fechaLlegada": "2025-03-07T03:37:50",
     * "precio": 220.00,
     * "asientosDisponibles": 20
     * }
     */
    @Operation(summary = "Listar todos los vuelos", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<List<VueloDTO>> listarVuelos() {
        List<VueloDTO> vuelos = vueloService.listarVuelos();
        return vuelos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vuelos);
    }

    /**
     * Endpoint para buscar vuelo por origen y destino
     * Recibe params de origen y destino
     * Ejemplo de petición:
     * | key |-----------| value |
     * origen-------------mexico
     * destino-----------guadalajara
     * Respuesta:
     * {
     * "id": 4,
     * "aerolinea": "Aeromexico",
     * "origen": "mexico",
     * "destino": "guadalajara",
     * "fechaSalida": "2025-03-07T03:38:36",
     * "fechaLlegada": "2025-03-07T03:38:36",
     * "precio": 4000.00,
     * "asientosDisponibles": 40
     * }
     */
    @Operation(summary = "Buscar vuelos por origen y destino", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/buscar")
    public ResponseEntity<List<VueloDTO>> buscarVuelos(@RequestParam String origen,
                                                       @RequestParam String destino) {

        List<VueloDTO> vuelos = vueloService.buscarVuelos(origen, destino);
        return ResponseEntity.ok(vuelos);
    }

    /**
     * Endpoint para obtener vuelo por id
     * Respuesta:
     * {
     * "id": 4,
     * "aerolinea": "Aeromexico",
     * "origen": "mexico",
     * "destino": "guadalajara",
     * "fechaSalida": "2025-03-07T03:38:36",
     * "fechaLlegada": "2025-03-07T03:38:36",
     * "precio": 4000.00,
     * "asientosDisponibles": 40
     * }
     */
    @Operation(summary = "Buscar vuelos por id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/buscar/{id}")
    public ResponseEntity<VueloDTO> obtenerVueloPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vueloService.obtenerVueloPorId(id));
    }

    /**
     * Endpoint para crear un nuevo vuelo (Solo ADMIN)
     * Recibe un JSON con lo siguiente:
     * Ejemplo de petición:
     * {
     * "aerolinea": "Avianca",
     * "origen": "Bogotá",
     * "destino": "México",
     * "fechaSalida": "2025-03-10T10:00:00",
     * "fechaLlegada": "2025-03-10T14:00:00",
     * "precio": 1350.00,
     * "asientosDisponibles": 25
     * }
     * Respuesta:
     * {
     * "id": 6,
     * "aerolinea": "Avianca",
     * "origen": "Bogotá",
     * "fechaSalida": "2025-03-10T10:00:00",
     * "destino": "México",
     * "fechaLlegada": "2025-03-10T14:00:00",
     * "precio": 1350.00,
     * "asientosDisponibles": 25
     * }
     */
    @Operation(summary = "Crear un nuevo vuelo (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VueloDTO> crearVuelo(@RequestBody VueloDTO vueloDTO) {
        VueloDTO nuevoVuelo = vueloService.crearVuelo(vueloDTO);
        return ResponseEntity
                .created(URI.create("/vuelos/" + nuevoVuelo.getId()))
                .body(nuevoVuelo);
    }

    /**
     * Endpoint para actualizar un vuelo (Solo ADMIN)
     * Recibe un JSON como el siguiente:
     * Ejemplo de petición:
     * {
     * "aerolinea": "Latam Airlines",
     * "origen": "Bogotá",
     * "destino": "Nueva York",
     * "fechaSalida": "2025-03-15T08:00:00",
     * "fechaLlegada": "2025-03-15T14:00:00",
     * "precio": 450.00,
     * "asientosDisponibles": 15
     * }
     * Respuesta:
     * {
     * "id": 4,
     * "aerolinea": "Latam Airlines",
     * "origen": "Bogotá",
     * "destino": "Nueva York",
     * "fechaSalida": "2025-03-15T08:00:00",
     * "fechaLlegada": "2025-03-15T14:00:00",
     * "precio": 450.00,
     * "asientosDisponibles": 15
     * }
     */
    @Operation(summary = "Actualizar un vuelo (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VueloDTO> actualizarVuelo(@PathVariable Long id, @RequestBody VueloDTO vueloDTO) {
        VueloDTO vueloActualizado = vueloService.actualizarVuelo(id, vueloDTO);
        return ResponseEntity.ok(vueloActualizado);
    }
    /**
     * Endpoint para eliminar un vuelo (Solo ADMIN)
     * Respuesta:
     * 204 No Content
     */
    @Operation(summary = "Eliminar un vuelo (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVuelo(@PathVariable Long id) {
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }
}
