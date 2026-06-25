package com.codffee.backend.controller;

import com.codffee.backend.dto.PedidoRequest;
import com.codffee.backend.entity.DetallePedido;
import com.codffee.backend.entity.EstadoPedido;
import com.codffee.backend.entity.Pedido;
import com.codffee.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestion de pedidos, detalles y cambios de estado")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Obtiene todos los pedidos registrados. Requiere autenticacion.")
    @ApiResponse(responseCode = "200", description = "Listado de pedidos")
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Consulta un pedido especifico por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public Pedido buscarPorId(@Parameter(description = "ID del pedido", example = "1") @PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar pedidos por usuario", description = "Obtiene los pedidos asociados a un usuario.")
    @ApiResponse(responseCode = "200", description = "Listado de pedidos del usuario")
    public List<Pedido> listarPorUsuario(@Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return pedidoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar pedidos por estado", description = "Filtra pedidos por estado: PENDIENTE, PREPARANDO, LISTO, ENTREGADO o CANCELADO.")
    @ApiResponse(responseCode = "200", description = "Listado de pedidos filtrados")
    public List<Pedido> listarPorEstado(@Parameter(description = "Estado del pedido", example = "PENDIENTE") @PathVariable EstadoPedido estado) {
        return pedidoService.listarPorEstado(estado);
    }

    @GetMapping("/{pedidoId}/detalles")
    @Operation(summary = "Listar detalles de pedido", description = "Obtiene los productos y cantidades incluidos en un pedido.")
    @ApiResponse(responseCode = "200", description = "Detalle del pedido")
    public List<DetallePedido> listarDetallesPorPedido(@Parameter(description = "ID del pedido", example = "1") @PathVariable Long pedidoId) {
        return pedidoService.listarDetallesPorPedido(pedidoId);
    }

    @PostMapping
    @Operation(summary = "Crear pedido", description = "Registra un pedido con usuario, metodo de pago, observaciones y productos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado")
    })
    public Pedido crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        return pedidoService.crearPedido(pedidoRequest);
    }

    @PutMapping("/{pedidoId}/estado/{estado}")
    @Operation(summary = "Cambiar estado de pedido", description = "Actualiza el estado operativo de un pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public Pedido cambiarEstado(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long pedidoId,
            @Parameter(description = "Nuevo estado", example = "PREPARANDO") @PathVariable EstadoPedido estado
    ) {
        return pedidoService.cambiarEstado(pedidoId, estado);
    }

    @PutMapping("/{pedidoId}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Marca un pedido como cancelado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public void cancelarPedido(@Parameter(description = "ID del pedido", example = "1") @PathVariable Long pedidoId) {
        pedidoService.cancelarPedido(pedidoId);
    }
}
