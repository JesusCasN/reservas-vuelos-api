package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletoResponseDTO implements Serializable {
    private Long id;
    private Long reservaId;
    private String codigo;
    private String pdfUrl;
    private LocalDateTime fechaEmision;
}
