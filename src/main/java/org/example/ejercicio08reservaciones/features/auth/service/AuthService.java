package org.example.ejercicio08reservaciones.features.auth.service;

import org.example.ejercicio08reservaciones.features.auth.dto.UsuarioSesionDTO;

public interface AuthService {
    UsuarioSesionDTO obtenerUsuarioAutenticado(String username);
}
