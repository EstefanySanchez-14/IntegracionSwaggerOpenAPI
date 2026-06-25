package com.codffee.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Prueba", description = "Endpoint publico para verificar disponibilidad del backend")
public class PruebaController {

    @GetMapping("/api/prueba")
    @Operation(summary = "Probar backend", description = "Devuelve un mensaje simple para confirmar que la API esta funcionando.")
    @ApiResponse(responseCode = "200", description = "Backend disponible")
    public String prueba() {
        return "Backend Codffee funcionando correctamente";
    }
}