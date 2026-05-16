package com.codffee.backend.dto;

import com.codffee.backend.entity.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    private String observaciones;

    @Valid
    @NotEmpty(message = "El pedido debe contener al menos un producto")
    private List<DetallePedidoRequest> productos;

    public PedidoRequest() {
    }

    public PedidoRequest(Long usuarioId, MetodoPago metodoPago, String observaciones, List<DetallePedidoRequest> productos) {
        this.usuarioId = usuarioId;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
        this.productos = productos;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<DetallePedidoRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<DetallePedidoRequest> productos) {
        this.productos = productos;
    }
}