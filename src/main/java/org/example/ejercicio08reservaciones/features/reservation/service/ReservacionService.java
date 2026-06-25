package org.example.ejercicio08reservaciones.features.reservation.service;

import org.example.ejercicio08reservaciones.features.reservation.dto.CreateReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.ReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.UpdateReservacionDTO;

import java.util.List;

public interface ReservacionService {
    ReservacionDTO createReservacion(CreateReservacionDTO dto, String username);
    List<ReservacionDTO> readAllReservaciones();
    ReservacionDTO readById(Long id, String username);
    List<ReservacionDTO> readByUsuario(Long idUsuario, String username);
    List<ReservacionDTO> readByCuarto(Long idCuarto);
    ReservacionDTO updateReservacion(Long id, UpdateReservacionDTO dto, String username);
    ReservacionDTO cancelarReservacion(Long id, String username);
    void deleteReservacion(Long id);
}
