package com.api.service;

import com.api.dto.VueloDTO;
import com.api.model.Vuelo;

import java.util.List;

public interface VueloService {
    List<VueloDTO> listarVuelos();
    VueloDTO obtenerVueloPorId(Long id);
    VueloDTO crearVuelo(VueloDTO vueloDTO);
    VueloDTO actualizarVuelo(Long id, VueloDTO vueloDTO);
    void eliminarVuelo(Long id);
    List<VueloDTO> buscarVuelos(String origen, String destino);
}
