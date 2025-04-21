package com.api.repository;

import com.api.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    Optional<Boleto> findByCodigo(String codigo);

    @Query("SELECT b FROM Boleto b JOIN FETCH b.reserva")
    List<Boleto> findAllConReserva();

    @Query("SELECT b FROM Boleto b JOIN FETCH b.reserva WHERE b.codigo = :codigo")
    Optional<Boleto> findByCodigoConReserva(String codigo);

}
