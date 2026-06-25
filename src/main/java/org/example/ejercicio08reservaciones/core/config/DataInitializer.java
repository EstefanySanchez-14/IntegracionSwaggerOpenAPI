package org.example.ejercicio08reservaciones.core.config;

import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.core.domain.Rol;
import org.example.ejercicio08reservaciones.core.domain.Usuario;
import org.example.ejercicio08reservaciones.core.repository.RolRepository;
import org.example.ejercicio08reservaciones.core.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Rol adminRol = rolRepository.findByNombreRol("ROLE_ADMIN").orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombreRol("ROLE_ADMIN");
            rol.setDescripcionRol("Administrador del sistema");
            rol.setFecha(LocalDateTime.now());
            return rolRepository.save(rol);
        });

        Rol userRol = rolRepository.findByNombreRol("ROLE_USER").orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombreRol("ROLE_USER");
            rol.setDescripcionRol("Usuario regular");
            rol.setFecha(LocalDateTime.now());
            return rolRepository.save(rol);
        });

        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@ejercicio08.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setHabilitado(true);
            admin.setBloqueado(false);
            admin.setRols(List.of(adminRol));
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByUsername("user").isEmpty()) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setEmail("user@ejercicio08.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setHabilitado(true);
            user.setBloqueado(false);
            user.setRols(List.of(userRol));
            usuarioRepository.save(user);
        }
    }
}
