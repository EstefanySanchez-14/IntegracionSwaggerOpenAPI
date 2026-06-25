package org.example.ejercicio08reservaciones.features.room.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDisponibilidad(
        @NotNull(message =
                "El status para la disponibilidad es obligatorio (está o no disponible(falso o verdadero))")
        Boolean disponible
) {
}
