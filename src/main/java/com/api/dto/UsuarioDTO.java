package com.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
