package com.codffee.backend.controller;

import com.codffee.backend.dto.UsuarioResponse;
import com.codffee.backend.entity.Usuario;
import com.codffee.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Administracion de usuarios, roles y estado de cuentas")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios")
    public List<UsuarioResponse> listarTodos() {
        return usuarioService.listarTodos()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar usuarios activos", description = "Obtiene usuarios con cuenta activa. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios activos")
    public List<UsuarioResponse> listarActivos() {
        return usuarioService.listarActivos()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Consulta la informacion publica de un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public UsuarioResponse buscarPorId(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return UsuarioResponse.fromEntity(usuario);
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo", description = "Consulta un usuario a partir de su correo electronico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public UsuarioResponse buscarPorCorreo(@Parameter(description = "Correo del usuario", example = "cliente@codffee.com") @PathVariable String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        return UsuarioResponse.fromEntity(usuario);
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un usuario con rol y estado. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public UsuarioResponse crear(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.crear(usuario);
        return UsuarioResponse.fromEntity(usuarioCreado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Modifica los datos, rol o estado de un usuario. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public UsuarioResponse actualizar(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizar(id, usuario);
        return UsuarioResponse.fromEntity(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina o desactiva un usuario segun la logica del servicio. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public void eliminar(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
