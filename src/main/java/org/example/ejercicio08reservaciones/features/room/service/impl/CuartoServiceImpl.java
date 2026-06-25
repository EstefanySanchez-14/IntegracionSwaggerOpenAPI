package org.example.ejercicio08reservaciones.features.room.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ejercicio08reservaciones.core.domain.Cuarto;
import org.example.ejercicio08reservaciones.core.exceptions.BussinesValidationException;
import org.example.ejercicio08reservaciones.core.exceptions.EntityNotFoundException;
import org.example.ejercicio08reservaciones.features.room.dto.CreateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.CuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateCuartoDTO;
import org.example.ejercicio08reservaciones.features.room.dto.UpdateDisponibilidad;
import org.example.ejercicio08reservaciones.features.room.repository.CuartoRepository;
import org.example.ejercicio08reservaciones.features.room.service.CuartoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuartoServiceImpl implements CuartoService {
    private final CuartoRepository cuartoRepository;
    /**
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public CuartoDTO createCuarto(CreateCuartoDTO dto) {
        //Validar que el cuarto no exista
        if(cuartoRepository.findByNumero(dto.numero()).isPresent()){
            throw new BussinesValidationException("El número de cuarto " + dto.numero() +
                    " ya está registrado");
        }
        Cuarto cuarto = mapearAEntidad(dto);
        cuarto.setDisponible(true);
        Cuarto cuartoGuardado = cuartoRepository.save(cuarto);
        return mapearADto(cuartoGuardado);
    }

    /**
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<CuartoDTO> readAllCuartos() {
        return cuartoRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuartoDTO> readCuartosDisponibles() {
        return cuartoRepository.findByDisponibleTrue()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public CuartoDTO readById(long id) {
        return cuartoRepository.findById(id)
                .map(this::mapearADto)
                .orElseThrow(()-> new EntityNotFoundException("El cuarto " + id + " no existe"));
    }

    // PUT
    @Override
    @Transactional
    public CuartoDTO updateCuarto(Long id, UpdateCuartoDTO dto) {
        Cuarto cuarto = cuartoRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("El cuarto " + id + " no existe"));
        
        if(dto.numero() != null && !dto.numero().equals(cuarto.getNumero())){
            if(cuartoRepository.findByNumero(dto.numero()).isPresent()){
                throw new BussinesValidationException("El número " +
                        dto.numero() + " ya está registrado");
            }
            cuarto.setNumero(dto.numero());
        }

        if(dto.tipo() != null) cuarto.setTipo(dto.tipo());
        if(dto.precio() != null) cuarto.setPrecio(dto.precio());
        if(dto.numeroCamas() > 0) cuarto.setNumeroCamas(dto.numeroCamas());

        return mapearADto(cuartoRepository.save(cuarto));
    }

    /** PATCH
     * @param id  identificador del cuarto
     * @param dto el objeto que contiene el nuevo estatus de la disponibilidad
     * @return
     */
    @Override
    @Transactional
    public CuartoDTO updateDisponibilidad(Long id, UpdateDisponibilidad dto) {
        Cuarto cuarto = cuartoRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("El cuarto " + id + " no existe"));
        cuarto.setDisponible(dto.disponible());
        return mapearADto(cuartoRepository.save(cuarto));
    }

    /**
     * @param id
     */
    @Override
    @Transactional
    public void deleteCuarto(Long id) {
        if(!cuartoRepository.existsById(id)){
            throw new EntityNotFoundException("El cuarto " + id + " no existe");
        }
        cuartoRepository.deleteById(id);
    }

    private Cuarto mapearAEntidad(CreateCuartoDTO dto) {
        if(dto == null) return null;

        Cuarto entidad = new Cuarto();
        entidad.setTipo(dto.tipo());
        entidad.setNumero(dto.numero());
        entidad.setPrecio(dto.precio());
        entidad.setNumeroCamas(dto.numeroCamas());
        return entidad;
    }

    private CuartoDTO mapearADto(Cuarto entidad) {
        if(entidad == null) return null;

        return new CuartoDTO(
                entidad.getId(),
                entidad.getTipo(),
                entidad.getNumero(),
                entidad.getPrecio(),
                entidad.getNumeroCamas(),
                entidad.getDisponible()
        );
    }
}
