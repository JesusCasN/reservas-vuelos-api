package com.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class CambioContrasenaDTO implements Serializable {

    @NotBlank(message = "La contraseña actual es obligatoria.")
    private String contrasenaActual;

    @NotBlank(message = "La nueva contraseña es obligatoria.")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres.")
    private String nuevaContrasena;
}
