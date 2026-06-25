package org.example.ejercicio08reservaciones.features.reservation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEstadoReservacionDTO(
        @NotBlank(message = "El estado es obligatorio")
        String estado
) {
}
