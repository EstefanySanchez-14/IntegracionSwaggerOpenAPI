package org.example.ejercicio08reservaciones.features.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.features.auth.dto.UsuarioSesionDTO;
import org.example.ejercicio08reservaciones.features.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operaciones de autenticación y sesión")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioSesionDTO> me(Authentication authentication) {
        return ResponseEntity.ok(authService.obtenerUsuarioAutenticado(authentication.getName()));
    }
}
