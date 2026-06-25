package org.example.ejercicio08reservaciones.core.repository;

import org.example.ejercicio08reservaciones.core.domain.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @EntityGraph(attributePaths = "rols")
    Optional<Usuario> findByUsername(String username);
}
