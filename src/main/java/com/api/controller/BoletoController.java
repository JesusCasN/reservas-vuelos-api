package com.api.controller;

import com.api.dto.BoletoRequestDTO;
import com.api.dto.BoletoResponseDTO;
import com.api.service.BoletoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boletos")
@RequiredArgsConstructor
@Tag(name = "Boletos", description = "Operaciones para gestionar los boletos")
public class BoletoController {

    private final BoletoService boletoService;

    /**
     * Endpoint para listar todos los boletos (Solo ADMIN)
     * Respuesta JSON:
     * {
     * "id": 10,
     * "reservaId": 10,
     * "codigo": "2f9846dc",
     * "pdfUrl": "https://misboletos.com/boletos/12135.pdf",
     * "fechaEmision": "2025-03-27T18:44:11"
     * }
     */
    @Operation(summary = "Listar todos los boletos (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponse(responseCode = "200", description = "Lista de boletos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<BoletoResponseDTO>> listarBoletos() {
        List<BoletoResponseDTO> boletos = boletoService.listarBoletos();
        return boletos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(boletos);
    }

    /**
     * Endpoint para obtener boleto por codigo (CLIENTE o ADMIN)
     * Respuesta JSON:
     * {
     * "id": 10,
     * "reservaId": 10,
     * "codigo": "2f9846dc",
     * "pdfUrl": "https://misboletos.com/boletos/12135.pdf",
     * "fechaEmision": "2025-03-27T18:44:11"
     * }
     */
    @Operation(summary = "Obtener un boleto por codigo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Boleto encontrado exitosamente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @GetMapping("/{codigo}")
    public ResponseEntity<BoletoResponseDTO> obtenerBoletoPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(boletoService.obtenerBoletoPorCodigo(codigo));
    }

    /**
     * Endpoint para generar boleto (CLIENTE o ADMIN)
     * Recibe un JSON con pdfUrl.
     * Ejemplo de petición:
     * {
     * "pdfUrl": "https://misboletos.com/boletos/12135.pdf"
     * }
     * Respuesta:
     * {
     * "id": 10,
     * "reservaId": 10,
     * "codigo": "2f9846dc",
     * "pdfUrl": "https://misboletos.com/boletos/12135.pdf",
     * "fechaEmision": "2025-03-27T18:44:11.3002546"
     * }
     */
    @Operation(summary = "Generar boleto para una reserva", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Boleto creado exitosamente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @PostMapping("/{reservaId}")
    public ResponseEntity<BoletoResponseDTO> generarBoleto(@PathVariable Long reservaId,
                                                           @Valid @RequestBody BoletoRequestDTO boletoRequestDTO) {

        BoletoResponseDTO nuevoBoleto = boletoService.generarBoleto(reservaId, boletoRequestDTO);

        return ResponseEntity
                .created(URI.create("/boletos/" + nuevoBoleto.getCodigo()))
                .body(nuevoBoleto);
    }

    /**
     * Endpoint para eliminar boletos (Solo ADMIN)
     * Respuesta:
     * 204 No Content
     */
    @Operation(summary = "Eliminar boleto", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Boleto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleto no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleto(@PathVariable Long id) {
        boletoService.eliminarBoleto(id);
        return ResponseEntity.noContent().build();
    }

}
