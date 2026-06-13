import api from './api'

export const obtenerProductosDisponibles = () => {
  return api.get('/productos/disponibles')
}
