package com.api.service.impl;

import com.api.dto.BoletoRequestDTO;
import com.api.dto.BoletoResponseDTO;
import com.api.exception.BoletoNoEncontradoException;
import com.api.exception.ReservaNoEncontradaException;
import com.api.mapper.BoletoMapper;
import com.api.model.Boleto;
import com.api.model.Reserva;
import com.api.repository.BoletoRepository;
import com.api.repository.ReservaRepository;
import com.api.service.BoletoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoletoServiceImpl implements BoletoService {

    private final BoletoRepository boletoRepository;
    private final ReservaRepository reservaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoletoResponseDTO> listarBoletos() {
        return boletoRepository.findAllConReserva().stream()
                .map(BoletoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BoletoResponseDTO obtenerBoletoPorCodigo(String codigo) {
        Boleto boleto = boletoRepository.findByCodigoConReserva(codigo)
                .orElseThrow(() -> new BoletoNoEncontradoException("Boleto no encontrado con el codigo " + codigo));
        return BoletoMapper.toDTO(boleto);
    }

    @Override
    @Transactional
    public BoletoResponseDTO generarBoleto(Long reservaId, BoletoRequestDTO boletoRequestDTO) {

        // Verificar si la reserva existe
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        // Generar código único de boleto
        String codigoBoleto = UUID.randomUUID().toString().substring(0, 8);

        // Crear el boleto
        Boleto boleto = BoletoMapper.toEntity(boletoRequestDTO, reserva);
        boleto.setCodigo(codigoBoleto);

        Boleto boletoGuardado = boletoRepository.save(boleto);

        return BoletoMapper.toDTO(boletoGuardado);
    }

    @Override
    @Transactional
    public void eliminarBoleto(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new BoletoNoEncontradoException("Boleto no encontrado con ID: " + id));
        boletoRepository.delete(boleto);
    }
}
