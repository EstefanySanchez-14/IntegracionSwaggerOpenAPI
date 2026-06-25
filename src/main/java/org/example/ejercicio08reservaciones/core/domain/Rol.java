package org.example.ejercicio08reservaciones.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Rol")

public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRol;
    @Column(length = 50, nullable = false)
    private String nombreRol;
    @Column(length = 150, nullable = false)
    private String descripcionRol;
    @Column(nullable = false)
    private LocalDateTime fecha;
}
