package org.example.ejercicio08reservaciones.features.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.core.domain.Rol;
import org.example.ejercicio08reservaciones.core.domain.Usuario;
import org.example.ejercicio08reservaciones.core.exceptions.EntityNotFoundException;
import org.example.ejercicio08reservaciones.core.repository.UsuarioRepository;
import org.example.ejercicio08reservaciones.features.auth.dto.UsuarioSesionDTO;
import org.example.ejercicio08reservaciones.features.auth.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UsuarioSesionDTO obtenerUsuarioAutenticado(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + username));

        return new UsuarioSesionDTO(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getHabilitado(),
                usuario.getBloqueado(),
                usuario.getRols().stream()
                        .map(Rol::getNombreRol)
                        .toList()
        );
    }
}
