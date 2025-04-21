package com.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioRegistroDTO implements Serializable {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El correo debe tener un formato válido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    @NotBlank(message = "El rol es obligatorio.")
    private String rol;
}
