package com.api.repository;

import com.api.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    List<Vuelo> findByOrigenAndDestino(String origen, String destino);

}
