import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import MenuPage from './pages/MenuPage'
import { obtenerToken } from './services/authService'

function RutaProtegida({ children }) {
  const token = obtenerToken()

  if (!token) {
    return <Navigate to="/login" replace />
  }

  return children
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />

        <Route path="/login" element={<LoginPage />} />

        <Route
          path="/menu"
          element={
            <RutaProtegida>
              <MenuPage />
            </RutaProtegida>
          }
        />
      </Routes>
    </BrowserRouter>
  )
}

export default App