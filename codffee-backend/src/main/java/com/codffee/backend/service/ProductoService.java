package com.codffee.backend.service;

import com.codffee.backend.entity.Categoria;
import com.codffee.backend.entity.Producto;
import com.codffee.backend.exception.RecursoNoEncontradoException;
import com.codffee.backend.repository.CategoriaRepository;
import com.codffee.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> listarDisponibles() {
        return productoRepository.findByDisponibleTrue();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> listarDisponiblesPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndDisponibleTrue(categoriaId);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
    }

    public Producto crear(Producto producto) {
        Long categoriaId = producto.getCategoria().getId();

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + categoriaId));

        producto.setId(null);
        producto.setCategoria(categoria);
        producto.setDisponible(true);

        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = buscarPorId(id);

        Long categoriaId = productoActualizado.getCategoria().getId();

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + categoriaId));

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setImagenUrl(productoActualizado.getImagenUrl());
        producto.setDisponible(productoActualizado.getDisponible());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        producto.setDisponible(false);
        productoRepository.save(producto);
    }
}