package com.api.mapper;

import com.api.dto.BoletoRequestDTO;
import com.api.dto.BoletoResponseDTO;
import com.api.model.Boleto;
import com.api.model.Reserva;

import java.time.LocalDateTime;

public class BoletoMapper {

    // Convierte el RequestDTO a entidad
    public static Boleto toEntity(BoletoRequestDTO requestDTO, Reserva reserva) {
        if (requestDTO == null || reserva == null) {
            return null;
        }
        Boleto boleto = new Boleto();
        boleto.setReserva(reserva);
        boleto.setPdfUrl(requestDTO.getPdfUrl());
        boleto.setFechaEmision(LocalDateTime.now());
        // El código del boleto se asigna luego en el servicio
        return boleto;
    }

    // Convierte la entidad a ResponseDTO
    public static BoletoResponseDTO toDTO(Boleto boleto) {
        if (boleto == null) {
            return null;
        }
        return new BoletoResponseDTO(
                boleto.getId(),
                boleto.getReserva().getId(),
                boleto.getCodigo(),
                boleto.getPdfUrl(),
                boleto.getFechaEmision()
        );
    }
}
