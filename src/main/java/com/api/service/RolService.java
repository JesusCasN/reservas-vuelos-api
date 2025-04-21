package com.api.service;

import com.api.model.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listarRoles();
    Optional<Rol> obtenerRolPorId(Long id);
    Rol crearRol(Rol rol);
    Rol actualizarRol(Long id, Rol rol);
    void eliminarRol(Long id);
}
