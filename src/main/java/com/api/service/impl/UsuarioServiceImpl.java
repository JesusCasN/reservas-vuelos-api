package com.api.service.impl;

import com.api.dto.CambioContrasenaDTO;
import com.api.dto.UsuarioDTO;
import com.api.dto.UsuarioRegistroDTO;
import com.api.exception.ContrasenaIncorrectaException;
import com.api.exception.RolNoEncontradoException;
import com.api.exception.UsuarioNoEncontradoException;
import com.api.exception.UsuarioYaExisteException;
import com.api.mapper.UsuarioMapper;
import com.api.model.Rol;
import com.api.model.Usuario;
import com.api.repository.RolRepository;
import com.api.repository.UsuarioRepository;
import com.api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAllConRol().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdConRol(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioRegistroDTO registroDTO) {

        if (usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
            throw new UsuarioYaExisteException(registroDTO.getEmail());
        }

        Rol rol = rolRepository.findByNombre(registroDTO.getRol())
                .orElseThrow(() -> new RolNoEncontradoException(registroDTO.getRol()));

        Usuario usuario = UsuarioMapper.toEntity(registroDTO);
        usuario.setRol(rol);
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        //guardar usuario en la base de datos
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        //retornar el DTO generado
        return UsuarioMapper.toDTO(nuevoUsuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        // Buscar al usuario por ID
        Usuario usuarioExistente  = usuarioRepository.findByIdConRol(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        // Buscar el rol correspondiente
        Rol rol = rolRepository.findByNombre(usuarioDTO.getRol())
                .orElseThrow(() -> new RolNoEncontradoException(usuarioDTO.getRol()));

        // Actualizar solo los campos necesarios
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setRol(rol);

        // Guardamos los cambios
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void actualizarContrasena(Long idUsuario, CambioContrasenaDTO cambioContrasenaDTO) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException(idUsuario));

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(cambioContrasenaDTO.getContrasenaActual(), usuario.getPassword())) {
            throw new ContrasenaIncorrectaException("La contraseña actual es incorrecta");
        }

        // Validar que la nueva contraseña sea diferente a la actual
        if (passwordEncoder.matches(cambioContrasenaDTO.getNuevaContrasena(), usuario.getPassword())) {
            throw new ContrasenaIncorrectaException("La nueva contraseña no puede ser igual a la anterior.");
        }

        // Encriptar y actualizar la nueva contraseña
        usuario.setPassword(passwordEncoder.encode(cambioContrasenaDTO.getNuevaContrasena()));
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuarioRepository.delete(usuario);
    }


}
