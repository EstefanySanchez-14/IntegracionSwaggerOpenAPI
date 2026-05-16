package com.codffee.backend.service;

import com.codffee.backend.entity.Categoria;
import com.codffee.backend.exception.RecursoNoEncontradoException;
import com.codffee.backend.exception.SolicitudInvalidaException;
import com.codffee.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarActivas() {
        return categoriaRepository.findByActivoTrue();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + id));
    }

    public Categoria crear(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new SolicitudInvalidaException("Ya existe una categoría con ese nombre");
        }

        categoria.setId(null);
        categoria.setActivo(true);

        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria categoriaActualizada) {
        Categoria categoria = buscarPorId(id);

        if (!categoria.getNombre().equals(categoriaActualizada.getNombre())
                && categoriaRepository.existsByNombre(categoriaActualizada.getNombre())) {
            throw new SolicitudInvalidaException("Ya existe otra categoría con ese nombre");
        }

        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        categoria.setActivo(categoriaActualizada.getActivo());

        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }
}