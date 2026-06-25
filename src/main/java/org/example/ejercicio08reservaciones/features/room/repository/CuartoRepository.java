package org.example.ejercicio08reservaciones.features.room.repository;

import org.example.ejercicio08reservaciones.core.domain.Cuarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuartoRepository extends JpaRepository<Cuarto, Long> {
    Optional<Cuarto> findByNumero (int numero);
    List<Cuarto> findByDisponibleTrue();
}
