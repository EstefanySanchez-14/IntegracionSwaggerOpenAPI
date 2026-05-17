package com.codffee.backend.dto;

import com.codffee.backend.entity.Rol;
import com.codffee.backend.entity.Usuario;

public class LoginResponse {

    private String mensaje;
    private String token;
    private String tipoToken;
    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;

    public LoginResponse() {
    }

    public LoginResponse(String mensaje, String token, String tipoToken, Long id, String nombre, String correo, Rol rol) {
        this.mensaje = mensaje;
        this.token = token;
        this.tipoToken = tipoToken;
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public static LoginResponse fromEntity(Usuario usuario, String token) {
        return new LoginResponse(
                "Inicio de sesión exitoso",
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getToken() {
        return token;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Rol getRol() {
        return rol;
    }
}