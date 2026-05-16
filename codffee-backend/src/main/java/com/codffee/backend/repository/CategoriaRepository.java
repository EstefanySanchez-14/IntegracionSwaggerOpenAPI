package com.codffee.backend.repository;

import com.codffee.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByActivoTrue();

    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}