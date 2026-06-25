package com.codffee.backend.controller;

import com.codffee.backend.dto.LoginRequest;
import com.codffee.backend.dto.LoginResponse;
import com.codffee.backend.dto.RegisterRequest;
import com.codffee.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Endpoints para registro e inicio de sesion con JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Valida las credenciales del usuario y devuelve un token JWT tipo Bearer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea una cuenta de cliente y devuelve un token JWT para autenticacion inmediata.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos o correo ya registrado")
    })
    public LoginResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
