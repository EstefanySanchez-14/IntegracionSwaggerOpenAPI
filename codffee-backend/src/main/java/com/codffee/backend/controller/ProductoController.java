package com.codffee.backend.controller;

import com.codffee.backend.entity.Producto;
import com.codffee.backend.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }

    @GetMapping("/disponibles")
    public List<Producto> listarDisponibles() {
        return productoService.listarDisponibles();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> listarPorCategoria(@PathVariable Long categoriaId) {
        return productoService.listarPorCategoria(categoriaId);
    }

    @GetMapping("/categoria/{categoriaId}/disponibles")
    public List<Producto> listarDisponiblesPorCategoria(@PathVariable Long categoriaId) {
        return productoService.listarDisponiblesPorCategoria(categoriaId);
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PostMapping
    public Producto crear(@Valid @RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}