package com.codffee.backend.controller;

import com.codffee.backend.service.CorreoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/correos")
public class CorreoController {

    private final CorreoService correoService;

    public CorreoController(CorreoService correoService) {
        this.correoService = correoService;
    }

    @PostMapping("/prueba")
    public String enviarCorreoPrueba(@RequestParam String destinatario) {
        correoService.enviarCorreoSimple(
                destinatario,
                "Correo de prueba - Codffee",
                "Hola, este es un correo de prueba enviado desde el backend de Codffee."
        );

        return "Correo enviado correctamente a " + destinatario;
    }
}