package com.api.service;

import com.api.dto.CambioContrasenaDTO;
import com.api.dto.UsuarioDTO;
import com.api.dto.UsuarioRegistroDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerUsuarioPorId(Long id);
    UsuarioDTO crearUsuario(UsuarioRegistroDTO registroDTO);
    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO);
    void actualizarContrasena(Long idUsuario, CambioContrasenaDTO cambioContrasenaDTO);
    void eliminarUsuario(Long id);
}
