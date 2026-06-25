package org.example.ejercicio08reservaciones.core.config;

import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.core.domain.Usuario;
import org.example.ejercicio08reservaciones.core.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
            
            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getHabilitado(),
                    !usuario.getBloqueado(),
                    true,
                    true,
                    usuario.getRols().stream()
                            .map(rol -> new SimpleGrantedAuthority(rol.getNombreRol()))
                            .collect(Collectors.toList())
            );
        };
    }
}
