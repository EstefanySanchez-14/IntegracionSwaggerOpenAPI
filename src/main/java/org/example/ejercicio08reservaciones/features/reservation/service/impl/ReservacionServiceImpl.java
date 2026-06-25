package org.example.ejercicio08reservaciones.features.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.core.domain.Cuarto;
import org.example.ejercicio08reservaciones.core.domain.Reservacion;
import org.example.ejercicio08reservaciones.core.domain.Usuario;
import org.example.ejercicio08reservaciones.core.exceptions.BussinesValidationException;
import org.example.ejercicio08reservaciones.core.exceptions.EntityNotFoundException;
import org.example.ejercicio08reservaciones.core.repository.UsuarioRepository;
import org.example.ejercicio08reservaciones.features.reservation.dto.CreateReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.ReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.dto.UpdateReservacionDTO;
import org.example.ejercicio08reservaciones.features.reservation.repository.ReservacionRepository;
import org.example.ejercicio08reservaciones.features.reservation.service.ReservacionService;
import org.example.ejercicio08reservaciones.features.room.repository.CuartoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservacionServiceImpl implements ReservacionService {

    private final ReservacionRepository reservacionRepository;
    private final CuartoRepository cuartoRepository;
    private final UsuarioRepository usuarioRepository;

    private Usuario obtenerUsuarioAutenticado(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    private boolean esAdmin(Usuario usuario) {
        return usuario.getRols().stream()
                .anyMatch(r -> "ROLE_ADMIN".equals(r.getNombreRol()));
    }

    private void validarPropietarioOAdmin(Reservacion reservacion, Usuario usuarioAutenticado) {
        if (!esAdmin(usuarioAutenticado) && !reservacion.getUsuario().getIdUsuario().equals(usuarioAutenticado.getIdUsuario())) {
            throw new AccessDeniedException("No tienes permiso para acceder a esta reservación");
        }
    }

    private void validarUsuarioSolicitudOAdmin(Long idUsuarioSolicitado, Usuario usuarioAutenticado) {
        if (!esAdmin(usuarioAutenticado) && !idUsuarioSolicitado.equals(usuarioAutenticado.getIdUsuario())) {
            throw new AccessDeniedException("No tienes permiso para acceder a las reservaciones de otro usuario");
        }
    }

    @Override
    @Transactional
    public ReservacionDTO createReservacion(CreateReservacionDTO dto, String username) {
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado(username);
        validarUsuarioSolicitudOAdmin(dto.idUsuario(), usuarioAutenticado);

        if (dto.fechaSalida().isBefore(dto.fechaEntrada()) || dto.fechaSalida().isEqual(dto.fechaEntrada())) {
            throw new BussinesValidationException("La fecha de salida debe ser posterior a la fecha de entrada");
        }

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario con id " + dto.idUsuario() + " no existe"));

        Cuarto cuarto = cuartoRepository.findById(dto.idCuarto())
                .orElseThrow(() -> new EntityNotFoundException("El cuarto con id " + dto.idCuarto() + " no existe"));

        if (!Boolean.TRUE.equals(cuarto.getDisponible())) {
            throw new BussinesValidationException("El cuarto con id " + dto.idCuarto() + " no está disponible");
        }

        List<Reservacion> traslapes = reservacionRepository.findTraslapes(dto.idCuarto(), dto.fechaEntrada(), dto.fechaSalida());
        if (!traslapes.isEmpty()) {
            throw new BussinesValidationException("El cuarto ya tiene una reservación activa que se cruza con las fechas solicitadas");
        }

        Reservacion reservacion = mapearAEntidad(dto, usuario, cuarto);
        reservacion.setEstado("ACTIVA");
        reservacion.setFechaCreacion(LocalDateTime.now());

        Reservacion reservacionGuardada = reservacionRepository.save(reservacion);
        return mapearADto(reservacionGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionDTO> readAllReservaciones() {
        return reservacionRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservacionDTO readById(Long id, String username) {
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado(username);
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reservación " + id + " no existe"));
        
        validarPropietarioOAdmin(reservacion, usuarioAutenticado);
        return mapearADto(reservacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionDTO> readByUsuario(Long idUsuario, String username) {
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado(username);
        validarUsuarioSolicitudOAdmin(idUsuario, usuarioAutenticado);

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("El usuario con id " + idUsuario + " no existe");
        }
        return reservacionRepository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionDTO> readByCuarto(Long idCuarto) {
        if (!cuartoRepository.existsById(idCuarto)) {
            throw new EntityNotFoundException("El cuarto con id " + idCuarto + " no existe");
        }
        return reservacionRepository.findByCuarto_Id(idCuarto)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional
    public ReservacionDTO updateReservacion(Long id, UpdateReservacionDTO dto, String username) {
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado(username);
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reservación " + id + " no existe"));

        validarPropietarioOAdmin(reservacion, usuarioAutenticado);

        if (dto.fechaSalida().isBefore(dto.fechaEntrada()) || dto.fechaSalida().isEqual(dto.fechaEntrada())) {
            throw new BussinesValidationException("La fecha de salida debe ser posterior a la fecha de entrada");
        }

        List<Reservacion> traslapes = reservacionRepository.findTraslapesExcluyendo(
                reservacion.getCuarto().getId(), dto.fechaEntrada(), dto.fechaSalida(), id);
        
        if (!traslapes.isEmpty()) {
            throw new BussinesValidationException("El cuarto ya tiene una reservación activa que se cruza con las fechas solicitadas");
        }

        reservacion.setFechaEntrada(dto.fechaEntrada());
        reservacion.setFechaSalida(dto.fechaSalida());

        return mapearADto(reservacionRepository.save(reservacion));
    }

    @Override
    @Transactional
    public ReservacionDTO cancelarReservacion(Long id, String username) {
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado(username);
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reservación " + id + " no existe"));

        validarPropietarioOAdmin(reservacion, usuarioAutenticado);

        if ("CANCELADA".equals(reservacion.getEstado())) {
            throw new BussinesValidationException("La reservación ya se encuentra cancelada");
        }

        reservacion.setEstado("CANCELADA");
        return mapearADto(reservacionRepository.save(reservacion));
    }

    @Override
    @Transactional
    public void deleteReservacion(Long id) {
        if (!reservacionRepository.existsById(id)) {
            throw new EntityNotFoundException("La reservación " + id + " no existe");
        }
        reservacionRepository.deleteById(id);
    }

    private Reservacion mapearAEntidad(CreateReservacionDTO dto, Usuario usuario, Cuarto cuarto) {
        if (dto == null) return null;

        Reservacion entidad = new Reservacion();
        entidad.setUsuario(usuario);
        entidad.setCuarto(cuarto);
        entidad.setFechaEntrada(dto.fechaEntrada());
        entidad.setFechaSalida(dto.fechaSalida());
        return entidad;
    }

    private ReservacionDTO mapearADto(Reservacion entidad) {
        if (entidad == null) return null;

        return new ReservacionDTO(
                entidad.getIdReservacion(),
                entidad.getUsuario().getIdUsuario(),
                entidad.getCuarto().getId(),
                entidad.getFechaEntrada(),
                entidad.getFechaSalida(),
                entidad.getEstado(),
                entidad.getFechaCreacion()
        );
    }
}
