import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import { login, guardarSesion } from '../services/authService'

function LoginPage() {
  const navigate = useNavigate()

  const [form, setForm] = useState({
    correo: '',
    contrasena: '',
  })

  const [cargando, setCargando] = useState(false)

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setCargando(true)

    try {
      const data = await login(form)
      guardarSesion(data)

      Swal.fire({
        icon: 'success',
        title: 'Bienvenido',
        text: data.mensaje,
        timer: 1500,
        showConfirmButton: false,
      })

      navigate('/menu')
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error al iniciar sesión',
        text: error.response?.data?.mensaje || 'Credenciales incorrectas',
      })
    } finally {
      setCargando(false)
    }
  }

  return (
    <div className="login-page d-flex align-items-center justify-content-center">
      <div className="card shadow login-card">
        <div className="card-body p-4">
          <h1 className="text-center mb-2">Codffee</h1>
          <p className="text-center text-muted mb-4">
            Sistema de pedidos para cafetería
          </p>

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Correo</label>
              <input
                type="email"
                name="correo"
                className="form-control"
                value={form.correo}
                onChange={handleChange}
                placeholder="admin@codffee.com"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Contraseña</label>
              <input
                type="password"
                name="contrasena"
                className="form-control"
                value={form.contrasena}
                onChange={handleChange}
                placeholder="123456"
                required
              />
            </div>

            <button className="btn btn-primary w-100" disabled={cargando}>
              {cargando ? 'Ingresando...' : 'Iniciar sesión'}
            </button>
          </form>

          <div className="mt-4 small text-muted">
            <p className="mb-1">Cuentas de prueba:</p>
            <p className="mb-1">Admin: admin@codffee.com / 123456</p>
            <p className="mb-1">Cliente: cliente@codffee.com / 123456</p>
            <p className="mb-0">Personal: personal@codffee.com / 123456</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default LoginPage