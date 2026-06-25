package org.example.ejercicio08reservaciones.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import org.example.ejercicio08reservaciones.core.domain.Rol;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Usuario")

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(name = "nombreDeUsuario", unique = true, nullable = false, length = 50)
    private String username;
    @Column(name = "claveDeUsuario", nullable = false, length = 200)
    private String password;
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;
    @Column(name = "habilitado", nullable = false, length = 1)
    private Boolean habilitado;
    @Column(name = "bloqueado", nullable = false, length = 1)
    private Boolean bloqueado;
    @ManyToMany
    @JoinTable(
            name = "UsuarioRol",
            joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol", referencedColumnName = "idRol"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"idUsuario", "idRol"})
    )
    private List<Rol> rols;
}
