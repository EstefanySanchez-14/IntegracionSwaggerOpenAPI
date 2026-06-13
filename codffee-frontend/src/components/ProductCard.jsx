import { useState } from 'react'

function ProductCard({ producto, onAgregar }) {
  const [agregando, setAgregando] = useState(false)
  const sinStock = producto.stock === 0

  const handleAgregar = () => {
    setAgregando(true)
    onAgregar(producto)
    setTimeout(() => setAgregando(false), 500)
  }

  return (
    <article className="product-card">
      <div className="product-card-image">
        <div className="product-card-img-placeholder">
          <span className="material-symbols-outlined">local_cafe</span>
        </div>
        {sinStock ? (
          <span className="product-card-stock product-card-stock-out">Agotado</span>
        ) : producto.stock <= 5 ? (
          <span className="product-card-stock product-card-stock-low">Solo {producto.stock}!</span>
        ) : (
          <span className="product-card-stock product-card-stock-ok">{producto.stock} disp.</span>
        )}
      </div>
      <div className="product-card-body">
        <div className="product-card-header">
          <h3 className="product-card-title">{producto.nombre}</h3>
          <span className="product-card-price">${Number(producto.precio).toFixed(2)}</span>
        </div>
        {producto.descripcion && (
          <p className="product-card-desc">{producto.descripcion}</p>
        )}
        {producto.categoria && (
          <span className="product-card-categoria">{producto.categoria.nombre || producto.categoria}</span>
        )}
        <button
          className="btn-codffee-primary product-card-btn"
          onClick={handleAgregar}
          disabled={sinStock || agregando}
        >
          <span className="material-symbols-outlined">add_shopping_cart</span>
          {agregando ? 'Agregado' : 'Agregar al carrito'}
        </button>
      </div>
    </article>
  )
}

export default ProductCard
