package com.codffee.backend.service;

import com.codffee.backend.dto.LoginRequest;
import com.codffee.backend.dto.LoginResponse;
import com.codffee.backend.entity.Usuario;
import com.codffee.backend.exception.SolicitudInvalidaException;
import com.codffee.backend.repository.UsuarioRepository;
import com.codffee.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo())
                .orElseThrow(() -> new SolicitudInvalidaException("Correo o contraseña incorrectos"));

        if (!usuario.getActivo()) {
            throw new SolicitudInvalidaException("El usuario está desactivado");
        }

        boolean passwordValida = passwordEncoder.matches(
                loginRequest.getContrasena(),
                usuario.getContrasena()
        );

        if (!passwordValida) {
            throw new SolicitudInvalidaException("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario);

        return LoginResponse.fromEntity(usuario, token);
    }
}