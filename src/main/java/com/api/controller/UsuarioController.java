package com.api.controller;

import com.api.dto.CambioContrasenaDTO;
import com.api.dto.UsuarioDTO;
import com.api.dto.UsuarioRegistroDTO;
import com.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Endpoint para listar todos los usuarios (Solo ADMIN)
     * Respuesta ejemplo:
     * {
     * "id": 3,
     * "nombre": "Carlos gomez",
     * "email": ""carlos@example.com"",
     * "rol": "CLIENTE"
     * }
     */
    @Operation(summary = "Listar todos los usuarios (solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para obtener usuario por ID (ADMIN o el mismo usuario)
     * Respuesta ejemplo:
     * {
     * "id": 14,
     * "nombre": "Jose de Jesus",
     * "email": "cliente@example.com",
     * "rol": "CLIENTE"
     * }
     */
    @Operation(summary = "Obtener un usuario por ID (solo ADMIN o el propio usuario)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    /**
     * Endpoint para registrar usuario (Público)
     * Recibe un JSON con nombre, email, password (la cual es encriptada en la base de datos) y rol (CLIENTE o ADMIN)
     * Ejemplo de petición:
     * {
     * "nombre": "Jose Nolasco",
     * "email": "admin@example.com",
     * "password": "12345",
     * "rol": "ADMIN"
     * }
     * Respuesta:
     * {
     * "id": 15,
     * "nombre": "Jose Nolasco",
     * "email": "admin@example.com",
     * "rol": "ADMIN"
     * }
     */
    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        UsuarioDTO usuarioDTO = usuarioService.crearUsuario(usuarioRegistroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    /**
     * Endpoint para actualizar usuario (Solo el propio usuario o ADMIN)
     * Recibe un JSON con nombre, email y rol
     * Ejemplo de petición:
     * {
     * "nombre": "Jose de Jesus",
     * "email": "cliente@example.com",
     * "rol": "CLIENTE"
     * }
     * Respuesta:
     * {
     * "id": 14,
     * "nombre": "Jose Castillo",
     * "email": "cliente@example.com",
     * "rol": "CLIENTE"
     * }
     */
    @Operation(summary = "Actualizar un usuario (solo ADMIN o el propio usuario)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id,
                                                        @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioDTO));
    }

    /**
     * Endpoint para actualizar password (CLIENTE o ADMIN)
     * Recibe un JSON con contraseña actual y nueva contraseña.
     * Ejemplo de petición:
     * {
     * "contrasenaActual": "123456",
     * "nuevaContrasena": "jesus123"
     * }
     * Respuesta:
     * 204 No Content
     */
    @Operation(summary = "Actualizar password (solo ADMIN o el propio usuario)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    @PatchMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Void> cambiarContrasena(@PathVariable Long id,
                                                  @RequestBody CambioContrasenaDTO cambioContrasenaDTO) {
        usuarioService.actualizarContrasena(id, cambioContrasenaDTO);
        return ResponseEntity.noContent().build();
    }
    /**
     * Endpoint para eliminar usuarios (Solo ADMIN)
     * Respuesta:
     * 204 No Content
     */
    @Operation(summary = "Eliminar un usuario (solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
