package com.codffee.backend.controller;

import com.codffee.backend.entity.Categoria;
import com.codffee.backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Administracion de categorias del catalogo de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Obtiene todas las categorias registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de categorias")
    public List<Categoria> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/activas")
    @Operation(summary = "Listar categorias activas", description = "Obtiene las categorias activas disponibles para el catalogo publico.")
    @ApiResponse(responseCode = "200", description = "Listado de categorias activas")
    public List<Categoria> listarActivas() {
        return categoriaService.listarActivas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Consulta los datos de una categoria especifica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public Categoria buscarPorId(@Parameter(description = "ID de la categoria", example = "1") @PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear categoria", description = "Registra una nueva categoria. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public Categoria crear(@Valid @RequestBody Categoria categoria) {
        return categoriaService.crear(categoria);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoria", description = "Modifica una categoria existente. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public Categoria actualizar(@Parameter(description = "ID de la categoria", example = "1") @PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        return categoriaService.actualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoria", description = "Elimina o desactiva una categoria segun la logica del servicio. Requiere rol ADMIN o PERSONAL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public void eliminar(@Parameter(description = "ID de la categoria", example = "1") @PathVariable Long id) {
        categoriaService.eliminar(id);
    }
}
