import api from './api'

export const crearPedido = (payload) => {
  return api.post('/pedidos', payload)
}

export const obtenerPedidos = () => {
  return api.get('/pedidos')
}

export const obtenerPedidosPorUsuario = (usuarioId) => {
  return api.get(`/pedidos/usuario/${usuarioId}`)
}

export const obtenerDetallesPedido = (pedidoId) => {
  return api.get(`/pedidos/${pedidoId}/detalles`)
}

export const cambiarEstadoPedido = (pedidoId, estado) => {
  return api.put(`/pedidos/${pedidoId}/estado/${estado}`)
}

export const cancelarPedido = (pedidoId) => {
  return api.put(`/pedidos/${pedidoId}/cancelar`)
}
