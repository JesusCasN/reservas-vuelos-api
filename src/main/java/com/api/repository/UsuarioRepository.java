package com.api.repository;

import com.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol")
    List<Usuario> findAllConRol();

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol r WHERE u.id = :id")
    Optional<Usuario> findByIdConRol(@Param("id") Long id);

}
