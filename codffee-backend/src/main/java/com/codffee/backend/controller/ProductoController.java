package com.codffee.backend.controller;

import com.codffee.backend.entity.Producto;
import com.codffee.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestion del catalogo de productos de la cafeteria")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos registrados, incluyendo productos no disponibles.")
    @ApiResponse(responseCode = "200", description = "Listado de productos")
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar productos disponibles", description = "Obtiene los productos activos para venta al cliente.")
    @ApiResponse(responseCode = "200", description = "Listado de productos disponibles")
    public List<Producto> listarDisponibles() {
        return productoService.listarDisponibles();
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar productos por categoria", description = "Obtiene los productos asociados a una categoria especifica.")
    @ApiResponse(responseCode = "200", description = "Productos de la categoria")
    public List<Producto> listarPorCategoria(@Parameter(description = "ID de la categoria", example = "1") @PathVariable Long categoriaId) {
        return productoService.listarPorCategoria(categoriaId);
    }

    @GetMapping("/categoria/{categoriaId}/disponibles")
    @Operation(summary = "Listar productos disponibles por categoria", description = "Obtiene solo los productos disponibles de una categoria.")
    @ApiResponse(responseCode = "200", description = "Productos disponibles de la categoria")
    public List<Producto> listarDisponiblesPorCategoria(@Parameter(description = "ID de la categoria", example = "1") @PathVariable Long categoriaId) {
        return productoService.listarDisponiblesPorCategoria(categoriaId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Consulta el detalle de un producto del catalogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public Producto buscarPorId(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public Producto crear(@Valid @RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public Producto actualizar(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina o desactiva un producto segun la logica del servicio. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public void eliminar(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        productoService.eliminar(id);
    }
}
