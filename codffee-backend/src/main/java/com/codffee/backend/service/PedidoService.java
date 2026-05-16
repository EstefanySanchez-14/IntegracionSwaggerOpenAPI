package com.codffee.backend.service;

import com.codffee.backend.dto.DetallePedidoRequest;
import com.codffee.backend.dto.PedidoRequest;
import com.codffee.backend.entity.*;
import com.codffee.backend.exception.RecursoNoEncontradoException;
import com.codffee.backend.exception.SolicitudInvalidaException;
import com.codffee.backend.repository.DetallePedidoRepository;
import com.codffee.backend.repository.PedidoRepository;
import com.codffee.backend.repository.ProductoRepository;
import com.codffee.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            DetallePedidoRepository detallePedidoRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<DetallePedido> listarDetallesPorPedido(Long pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }

    @Transactional
    public Pedido crearPedido(PedidoRequest pedidoRequest) {
        Usuario usuario = usuarioRepository.findById(pedidoRequest.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + pedidoRequest.getUsuarioId()));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setMetodoPago(pedidoRequest.getMetodoPago());
        pedido.setObservaciones(pedidoRequest.getObservaciones());
        pedido.setTotal(BigDecimal.ZERO);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;

        for (DetallePedidoRequest item : pedidoRequest.getProductos()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + item.getProductoId()));

            if (!producto.getDisponible()) {
                throw new SolicitudInvalidaException("El producto no está disponible: " + producto.getNombre());
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new SolicitudInvalidaException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            detallePedidoRepository.save(detalle);

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            total = total.add(subtotal);
        }

        pedidoGuardado.setTotal(total);

        return pedidoRepository.save(pedidoGuardado);
    }

    @Transactional
    public Pedido cambiarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);

        if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new SolicitudInvalidaException("No se puede cancelar un pedido que ya fue entregado");
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new SolicitudInvalidaException("El pedido ya está cancelado");
        }

        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(pedidoId);

        for (DetallePedido detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
}