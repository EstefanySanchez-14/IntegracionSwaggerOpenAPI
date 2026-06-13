import api from './api'

export const descargarReporteGeneral = () => {
  return api.get('/reportes/pedidos/pdf', { responseType: 'blob' })
}

export const descargarReporteFiltrado = (fechaInicio, fechaFin, estado) => {
  let url = `/reportes/pedidos/pdf/filtrado?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
  if (estado && estado !== '') {
    url += `&estado=${estado}`
  }
  return api.get(url, { responseType: 'blob' })
}
