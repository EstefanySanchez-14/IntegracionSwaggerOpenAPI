package org.example.ejercicio08reservaciones.core.exceptions;

import java.time.LocalDateTime;

public record CustomErrorRecord(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
