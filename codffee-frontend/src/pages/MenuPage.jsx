import { useNavigate } from 'react-router-dom'
import { obtenerUsuario, logout } from '../services/authService'

function MenuPage() {
  const navigate = useNavigate()
  const usuario = obtenerUsuario()

  const cerrarSesion = () => {
    logout()
    navigate('/login')
  }

  return (
    <div className="container py-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h1>Menú Codffee</h1>
          <p className="text-muted">
            Bienvenido, {usuario?.nombre} | Rol: {usuario?.rol}
          </p>
        </div>

        <button className="btn btn-outline-danger" onClick={cerrarSesion}>
          Cerrar sesión
        </button>
      </div>

      <div className="alert alert-success">
        Login conectado correctamente con el backend 🚀
      </div>
    </div>
  )
}

export default MenuPage