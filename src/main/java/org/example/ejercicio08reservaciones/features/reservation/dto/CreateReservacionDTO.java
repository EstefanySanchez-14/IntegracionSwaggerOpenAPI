package org.example.ejercicio08reservaciones.features.reservation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateReservacionDTO(
        @NotNull(message = "El id del usuario es obligatorio")
        @Positive(message = "El id del usuario debe ser mayor a 0")
        Long idUsuario,

        @NotNull(message = "El id del cuarto es obligatorio")
        @Positive(message = "El id del cuarto debe ser mayor a 0")
        Long idCuarto,

        @NotNull(message = "La fecha de entrada es obligatoria")
        @FutureOrPresent(message = "La fecha de entrada debe ser presente o futura")
        LocalDate fechaEntrada,

        @NotNull(message = "La fecha de salida es obligatoria")
        LocalDate fechaSalida
) {
}
