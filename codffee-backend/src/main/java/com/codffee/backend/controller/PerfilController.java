package com.codffee.backend.controller;

import com.codffee.backend.dto.PerfilRequest;
import com.codffee.backend.dto.UsuarioResponse;
import com.codffee.backend.entity.Usuario;
import com.codffee.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@Tag(name = "Perfil", description = "Consulta y actualizacion del usuario autenticado")
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Obtener perfil", description = "Devuelve los datos del usuario autenticado mediante JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil obtenido"),
            @ApiResponse(responseCode = "401", description = "Token ausente o invalido")
    })
    public UsuarioResponse obtenerPerfil(Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        return UsuarioResponse.fromEntity(usuario);
    }

    @PutMapping
    @Operation(summary = "Actualizar perfil", description = "Permite al usuario autenticado actualizar su nombre y contrasena.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente o invalido")
    })
    public UsuarioResponse actualizarPerfil(
            @RequestBody PerfilRequest perfilRequest,
            Authentication authentication
    ) {
        String correo = authentication.getName();
        Usuario usuario = usuarioService.buscarPorCorreo(correo);

        if (perfilRequest.getNombre() != null && !perfilRequest.getNombre().isBlank()) {
            usuario.setNombre(perfilRequest.getNombre());
        }

        usuario.setContrasena(perfilRequest.getContrasena());

        Usuario usuarioActualizado = usuarioService.actualizar(usuario.getId(), usuario);
        return UsuarioResponse.fromEntity(usuarioActualizado);
    }
}
