package org.example.ejercicio08reservaciones.features.archivo.repository;

import org.example.ejercicio08reservaciones.core.domain.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
}
