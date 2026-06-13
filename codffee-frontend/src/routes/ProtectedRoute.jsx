import { Navigate, Outlet } from 'react-router-dom'
import { obtenerToken } from '../services/authService'

function ProtectedRoute() {
  const token = obtenerToken()

  if (!token) {
    return <Navigate to="/login" replace />
  }

  return <Outlet />
}

export default ProtectedRoute
