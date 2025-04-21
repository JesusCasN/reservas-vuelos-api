package com.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloDTO implements Serializable {
    private Long id;
    private String aerolinea;
    private String origen;
    private String destino;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private BigDecimal precio;
    private int asientosDisponibles;
}
