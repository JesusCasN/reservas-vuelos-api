package com.api.service;

import com.api.dto.BoletoRequestDTO;
import com.api.dto.BoletoResponseDTO;
import java.util.List;

public interface BoletoService {
    List<BoletoResponseDTO> listarBoletos();
    BoletoResponseDTO obtenerBoletoPorCodigo(String codigo);
    BoletoResponseDTO generarBoleto(Long reservaId, BoletoRequestDTO boletoRequestDTO);
    void eliminarBoleto(Long id);
}
