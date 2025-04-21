package com.api.service.impl;

import com.api.exception.RolNoEncontradoException;
import com.api.model.Rol;
import com.api.repository.RolRepository;
import com.api.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    @Transactional
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public Rol actualizarRol(Long id, Rol rol) {
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con ID: " + id));

        rolExistente.setNombre(rol.getNombre());
        return rolRepository.save(rolExistente);
    }

    @Override
    @Transactional
    public void eliminarRol(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con ID: " + id));
        rolRepository.delete(rol);
    }
}
