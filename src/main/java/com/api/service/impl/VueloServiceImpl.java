package com.api.service.impl;

import com.api.dto.VueloDTO;
import com.api.exception.VueloNoEncontradoException;
import com.api.exception.VuelosNoEncontradosException;
import com.api.mapper.VueloMapper;
import com.api.model.Vuelo;
import com.api.repository.VueloRepository;
import com.api.service.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VueloServiceImpl implements VueloService {

    private final VueloRepository vueloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VueloDTO> listarVuelos() {
        return vueloRepository.findAll().stream()
                .map(VueloMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VueloDTO obtenerVueloPorId(Long id) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new VueloNoEncontradoException(id));
        return VueloMapper.toDTO(vuelo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VueloDTO> buscarVuelos(String origen, String destino) {
        List<Vuelo> vuelos = vueloRepository.findByOrigenAndDestino(origen, destino);

        if (vuelos.isEmpty()) {
            throw new VuelosNoEncontradosException("No se encontraron vuelos de " + origen + " a " + destino);
        }

        return vuelos.stream()
                .map(VueloMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VueloDTO crearVuelo(VueloDTO vueloDTO) {
        Vuelo vuelo = VueloMapper.toEntity(vueloDTO);
        Vuelo nuevoVuelo = vueloRepository.save(vuelo);
        return VueloMapper.toDTO(nuevoVuelo);
    }

    @Override
    @Transactional(readOnly = true)
    public VueloDTO actualizarVuelo(Long id, VueloDTO vueloDTO) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new VueloNoEncontradoException(id));

        VueloMapper.actualizarDesdeDTO(vuelo, vueloDTO);

        Vuelo actualizadoVuelo = vueloRepository.save(vuelo);
        return VueloMapper.toDTO(actualizadoVuelo);
    }

    @Override
    @Transactional(readOnly = true)
    public void eliminarVuelo(Long id) {
        Vuelo vuelo = vueloRepository.findById(id)
               .orElseThrow(() -> new VueloNoEncontradoException(id));
        vueloRepository.delete(vuelo);
    }
}
