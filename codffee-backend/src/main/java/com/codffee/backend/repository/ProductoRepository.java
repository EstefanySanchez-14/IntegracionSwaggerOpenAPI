package com.codffee.backend.repository;

import com.codffee.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByDisponibleTrue();

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByCategoriaIdAndDisponibleTrue(Long categoriaId);
}