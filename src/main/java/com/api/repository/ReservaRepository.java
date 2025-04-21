package com.api.repository;

import com.api.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario u JOIN FETCH r.vuelo v")
    List<Reserva> findAllConUsuarioYVuelo();

    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario JOIN FETCH r.vuelo WHERE r.usuario.id = :usuarioId")
    List<Reserva> findByUsuarioIdConUsuarioYVuelo(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario JOIN FETCH r.vuelo WHERE r.id = :id")
    Optional<Reserva> findByIdConUsuarioYVuelo(@Param("id") Long id);


}
