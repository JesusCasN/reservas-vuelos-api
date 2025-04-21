package com.api.mapper;

import com.api.dto.UsuarioDTO;
import com.api.dto.UsuarioRegistroDTO;
import com.api.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre() // Convertimos el rol a String
        );
    }


    // Convierte de UsuarioRegistroDTO a Usuario para la creación de usuarios
    public static Usuario toEntity(UsuarioRegistroDTO usuarioRegistroDTO) {
        if (usuarioRegistroDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRegistroDTO.getNombre());
        usuario.setEmail(usuarioRegistroDTO.getEmail());
        return usuario; // el rol y la contraseña se asignan en el servicio
    }
}
