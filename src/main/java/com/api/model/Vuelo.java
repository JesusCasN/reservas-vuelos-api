package com.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vuelos")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String aerolinea;
    @Column(nullable = false, length =100)
    private String origen;
    @Column(nullable = false, length = 100)
    private String destino;
    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;
    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;
    @Column(nullable = false)
    private BigDecimal precio;
    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;

}
