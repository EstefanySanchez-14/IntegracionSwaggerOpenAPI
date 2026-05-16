package com.codffee.backend.controller;

import com.codffee.backend.dto.PedidoRequest;
import com.codffee.backend.entity.DetallePedido;
import com.codffee.backend.entity.EstadoPedido;
import com.codffee.backend.entity.Pedido;
import com.codffee.backend.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> listarPorUsuario(@PathVariable Long usuarioId) {
        return pedidoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/estado/{estado}")
    public List<Pedido> listarPorEstado(@PathVariable EstadoPedido estado) {
        return pedidoService.listarPorEstado(estado);
    }

    @GetMapping("/{pedidoId}/detalles")
    public List<DetallePedido> listarDetallesPorPedido(@PathVariable Long pedidoId) {
        return pedidoService.listarDetallesPorPedido(pedidoId);
    }

    @PostMapping
    public Pedido crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        return pedidoService.crearPedido(pedidoRequest);
    }

    @PutMapping("/{pedidoId}/estado/{estado}")
    public Pedido cambiarEstado(
            @PathVariable Long pedidoId,
            @PathVariable EstadoPedido estado
    ) {
        return pedidoService.cambiarEstado(pedidoId, estado);
    }

    @PutMapping("/{pedidoId}/cancelar")
    public void cancelarPedido(@PathVariable Long pedidoId) {
        pedidoService.cancelarPedido(pedidoId);
    }
}