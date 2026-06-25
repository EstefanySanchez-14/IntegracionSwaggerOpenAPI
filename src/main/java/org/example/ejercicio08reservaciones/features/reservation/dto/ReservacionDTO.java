package org.example.ejercicio08reservaciones.features.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservacionDTO(
        Long idReservacion,
        Long idUsuario,
        Long idCuarto,
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        String estado,
        LocalDateTime fechaCreacion
) {
}
