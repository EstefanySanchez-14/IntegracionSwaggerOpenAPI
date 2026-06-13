import { Navigate, Outlet } from 'react-router-dom'
import { obtenerUsuario } from '../services/authService'

const redirectByRole = {
  CLIENTE: '/menu',
  PERSONAL: '/staff/pedidos',
  ADMIN: '/admin',
}

function RoleRoute({ roles }) {
  const usuario = obtenerUsuario()

  if (!usuario) {
    return <Navigate to="/login" replace />
  }

  if (!roles.includes(usuario.rol)) {
    const destino = redirectByRole[usuario.rol] || '/login'
    return <Navigate to={destino} replace />
  }

  return <Outlet />
}

export default RoleRoute
