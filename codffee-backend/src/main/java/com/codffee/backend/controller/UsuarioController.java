package com.codffee.backend.controller;

import com.codffee.backend.dto.UsuarioResponse;
import com.codffee.backend.entity.Usuario;
import com.codffee.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> listarTodos() {
        return usuarioService.listarTodos()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/activos")
    public List<UsuarioResponse> listarActivos() {
        return usuarioService.listarActivos()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return UsuarioResponse.fromEntity(usuario);
    }

    @GetMapping("/correo/{correo}")
    public UsuarioResponse buscarPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        return UsuarioResponse.fromEntity(usuario);
    }

    @PostMapping
    public UsuarioResponse crear(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.crear(usuario);
        return UsuarioResponse.fromEntity(usuarioCreado);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizar(id, usuario);
        return UsuarioResponse.fromEntity(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}