package com.api.controller;

import com.api.dto.JwtResponseDTO;
import com.api.dto.LoginDTO;
import com.api.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación y obtención del token JWT")
public class AuthController {

    // Se encarga de autenticar al usuario con sus credenciales
    private final AuthenticationManager authenticationManager;

    // Componente personalizado que genera el token JWT
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Endpoint para iniciar sesión como CLIENTE o ADMIN.
     * Recibe un JSON con email y password, y devuelve un token JWT si las credenciales son correctas.
     * Ejemplo de petición:
     * {
     *   "email": "cliente@example.com",
     *   "password": "12345"
     * }
     * Respuesta:
     * {
     *   "token jwt": "eyJhbGciOiJIUzI1NiIsInR..."
     * }
     */
    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario y obtener token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingreso exitoso y devuelve un token JWT"),
    })
    public ResponseEntity<JwtResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }


}
