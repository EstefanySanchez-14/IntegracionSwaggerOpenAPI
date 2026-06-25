package org.example.ejercicio08reservaciones.features.room.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.features.room.dto.CreateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.CuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateDisponibilidad;
import org.example.ejercicio08reservaciones.features.room.service.CuartoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuartos")
@RequiredArgsConstructor
@Tag(name = "Cuarto", description = "Operaciones para las habitaciones")
public class CuartoController {
    private final CuartoService cuartoService;

    @PostMapping
    public ResponseEntity<CuartoDTO> createCuarto(@Valid @RequestBody CreateCuartoDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cuartoService.createCuarto(dto));
    }

    @GetMapping
    public ResponseEntity<List<CuartoDTO>> findAllCuartos() {
        return ResponseEntity.ok(cuartoService.readAllCuartos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<CuartoDTO>> findCuartosDisponibles() {
        return ResponseEntity.ok(cuartoService.readCuartosDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuartoDTO> findCuarto(@PathVariable Long id) {
        return ResponseEntity.ok(cuartoService.readById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuartoDTO> updateCuarto(@PathVariable Long id, @Valid @RequestBody UpdateCuartoDTO dto) {
        return ResponseEntity.ok(cuartoService.updateCuarto(id, dto));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<CuartoDTO> updateCuartoDisponibilidad(@PathVariable Long id, @Valid @RequestBody UpdateDisponibilidad dto) {
        return  ResponseEntity.ok(cuartoService.updateDisponibilidad(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuarto(@PathVariable Long id) {
        cuartoService.deleteCuarto(id);
        return ResponseEntity.noContent().build();
    }

}
