package org.example.ejercicio08reservaciones.features.room.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateCuartoDTO(
        @NotBlank(message = "El tipo de cuarto es obligatorio")
        @Size(min = 4, max = 50, message = "El tipo debe estar entre 4 y 50 caracteres")
        String tipo,
        @NotNull(message = "El número es obligatorio")
        @Positive(message = "El número asignado al cuarto debe ser un valor positivo")
        Integer numero,
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        @Digits(integer = 8,
                fraction = 2,
                message = "El precio tiene un formato invalido (Máximo 8 enteros y 2 decimales")
        BigDecimal precio,
        @Min(value = 1, message = "El cuarto debe tener al menos 1 cama")
        int numeroCamas
) {
}
