package org.example.ejercicio08reservaciones.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "rooms")

public class Cuarto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;
    @Column(name = "numero", nullable = false, unique = true)
    private int numero;
    @Column(name = "precio", nullable = false, scale = 2, precision = 10)
    private BigDecimal precio;
    @Column(name = "numeroCamas", nullable = false)
    private int numeroCamas;
    @Column(name = "disponible", nullable = false)
    private Boolean disponible;
}
