import api from './api'

export const obtenerCategoriasActivas = () => {
  return api.get('/categorias/activas')
}
