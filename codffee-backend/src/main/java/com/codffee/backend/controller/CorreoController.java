package com.codffee.backend.controller;

import com.codffee.backend.service.CorreoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/correos")
@Tag(name = "Correos", description = "Pruebas de envio de correo electronico")
public class CorreoController {

    private final CorreoService correoService;

    public CorreoController(CorreoService correoService) {
        this.correoService = correoService;
    }

    @PostMapping("/prueba")
    @Operation(summary = "Enviar correo de prueba", description = "Envia un correo simple para validar la configuracion SMTP.")
    @ApiResponse(responseCode = "200", description = "Correo enviado correctamente")
    public String enviarCorreoPrueba(@Parameter(description = "Correo destino", example = "cliente@codffee.com") @RequestParam String destinatario) {
        correoService.enviarCorreoSimple(
                destinatario,
                "Correo de prueba - Codffee",
                "Hola, este es un correo de prueba enviado desde el backend de Codffee."
        );

        return "Correo enviado correctamente a " + destinatario;
    }
}