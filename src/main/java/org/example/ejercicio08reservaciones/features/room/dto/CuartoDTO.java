package org.example.ejercicio08reservaciones.features.room.dto;

import java.math.BigDecimal;

/**
 * DTO QUE VA A REPRESENTAR LA INFORMACIÓN QUE SE MUESTRA AL RECUPERAR LA HABITACIÓN
 * @param id
 * @param numero
 * @param precio
 * @param numeroCamas
 * @param disponible
 */

public record CuartoDTO(
        long id,
        String tipo,
        int numero,
        BigDecimal precio,
        int numeroCamas,
        Boolean disponible
) {
}
