package org.example.ejercicio08reservaciones.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
/**
 * DTO UTILIZADO PARA LA CREACIÓN DE U  NUEVO CUARTO
 * esta clase incluye las diversas validaciones para asegurar la integridad de los datos
 * antes de persistirlos
 * @param tipo Indica el tipo de habitación (sencilla, doble, suite...)
 * @param numero
 * @param precio
 * @param  numeroCamas
 */
public record CreateCuartoDTO(
        @Schema(example = "Suite Presidencial", description = "Tipo de la habitación")
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
