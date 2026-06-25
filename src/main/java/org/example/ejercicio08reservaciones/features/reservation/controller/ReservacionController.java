package org.example.ejercicio08reservaciones.features.reservation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.features.reservation.dto.CreateReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.ReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.UpdateReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.service.ReservacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservaciones")
@RequiredArgsConstructor
@Tag(name = "Reservacion", description = "Operaciones para la gestión de reservaciones")
public class ReservacionController {

    private final ReservacionService reservacionService;

    @PostMapping
    public ResponseEntity<ReservacionDTO> createReservacion(@Valid @RequestBody CreateReservacionDTO dto, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservacionService.createReservacion(dto, authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<List<ReservacionDTO>> findAllReservaciones() {
        return ResponseEntity.ok(reservacionService.readAllReservaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservacionDTO> findReservacion(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(reservacionService.readById(id, authentication.getName()));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservacionDTO>> findReservacionesByUsuario(@PathVariable Long idUsuario, Authentication authentication) {
        return ResponseEntity.ok(reservacionService.readByUsuario(idUsuario, authentication.getName()));
    }

    @GetMapping("/cuarto/{idCuarto}")
    public ResponseEntity<List<ReservacionDTO>> findReservacionesByCuarto(@PathVariable Long idCuarto) {
        return ResponseEntity.ok(reservacionService.readByCuarto(idCuarto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservacionDTO> updateReservacion(@PathVariable Long id, @Valid @RequestBody UpdateReservacionDTO dto, Authentication authentication) {
        return ResponseEntity.ok(reservacionService.updateReservacion(id, dto, authentication.getName()));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservacionDTO> cancelarReservacion(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(reservacionService.cancelarReservacion(id, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservacion(@PathVariable Long id) {
        reservacionService.deleteReservacion(id);
        return ResponseEntity.noContent().build();
    }
}
