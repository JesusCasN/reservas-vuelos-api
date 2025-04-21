package com.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vuelo_id", nullable = false)
    private Vuelo vuelo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;
    @Column(name = "fecha_reserva", nullable = false, updatable = false)
    private LocalDateTime fechaReserva;

}
