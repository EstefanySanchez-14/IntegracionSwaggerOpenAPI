package org.example.ejercicio08reservaciones.features.room.service;


import org.example.ejercicio08reservaciones.features.room.dto.CreateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.CuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateDisponibilidad;

import java.util.List;

public interface CuartoService {
    CuartoDTO createCuarto(CreateCuartoDTO dto);
    List<CuartoDTO> readAllCuartos();
    List<CuartoDTO> readCuartosDisponibles();
    CuartoDTO readById(long id);
    CuartoDTO updateCuarto(Long id, UpdateCuartoDTO dto);

    /**
     * @param id identificador del cuarto
     * @param dto el objeto que contiene el nuevo estatus de la disponibilidad
     * @return {@link CuartoDTO} actualizado
     */
    CuartoDTO updateDisponibilidad(Long id, UpdateDisponibilidad dto);
    void deleteCuarto(Long id);
}
