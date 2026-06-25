package org.example.ejercicio08reservaciones.features.auth.dto;

import java.util.List;

public record UsuarioSesionDTO(
        Long idUsuario,
        String username,
        String email,
        Boolean habilitado,
        Boolean bloqueado,
        List<String> roles
) {
}
