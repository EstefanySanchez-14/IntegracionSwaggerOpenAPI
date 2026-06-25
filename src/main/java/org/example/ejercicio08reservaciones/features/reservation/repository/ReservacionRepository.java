package org.example.ejercicio08reservaciones.features.reservation.repository;

import org.example.ejercicio08reservaciones.core.domain.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    List<Reservacion> findByUsuario_IdUsuario(Long idUsuario);

    List<Reservacion> findByCuarto_Id(Long idCuarto);

    @Query("SELECT r FROM Reservacion r WHERE r.cuarto.id = :idCuarto AND r.estado = 'ACTIVA' " +
           "AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada")
    List<Reservacion> findTraslapes(@Param("idCuarto") Long idCuarto,
                                    @Param("fechaEntrada") LocalDate fechaEntrada,
                                    @Param("fechaSalida") LocalDate fechaSalida);

    @Query("SELECT r FROM Reservacion r WHERE r.cuarto.id = :idCuarto AND r.estado = 'ACTIVA' " +
           "AND r.idReservacion != :idReservacion " +
           "AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada")
    List<Reservacion> findTraslapesExcluyendo(@Param("idCuarto") Long idCuarto,
                                              @Param("fechaEntrada") LocalDate fechaEntrada,
                                              @Param("fechaSalida") LocalDate fechaSalida,
                                              @Param("idReservacion") Long idReservacion);
}
