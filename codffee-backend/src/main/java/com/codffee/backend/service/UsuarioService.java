package com.codffee.backend.service;

import com.codffee.backend.entity.Rol;
import com.codffee.backend.entity.Usuario;
import com.codffee.backend.exception.RecursoNoEncontradoException;
import com.codffee.backend.exception.SolicitudInvalidaException;
import com.codffee.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new SolicitudInvalidaException("Ya existe un usuario con ese correo");
        }

        usuario.setId(null);
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getCorreo().equals(usuarioActualizado.getCorreo())
                && usuarioRepository.existsByCorreo(usuarioActualizado.getCorreo())) {
            throw new SolicitudInvalidaException("Ya existe otro usuario con ese correo");
        }

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setContrasena(usuarioActualizado.getContrasena());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setActivo(usuarioActualizado.getActivo());

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}