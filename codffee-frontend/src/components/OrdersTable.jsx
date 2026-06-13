import StatusBadge from './StatusBadge'

function OrdersTable({ pedidos, acciones }) {
  if (!pedidos || pedidos.length === 0) {
    return (
      <div className="empty-state">
        <span className="material-symbols-outlined empty-state-icon">receipt_long</span>
        <p className="font-body-md">No hay pedidos registrados</p>
      </div>
    )
  }

  return (
    <div className="orders-table-wrapper">
      <table className="orders-table">
        <thead>
          <tr>
            <th>Pedido</th>
            <th>Cliente</th>
            <th>Fecha</th>
            <th>Estado</th>
            <th>Total</th>
            <th className="text-center">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {pedidos.map((pedido) => (
            <tr key={pedido.id}>
              <td className="orders-table-id">#{pedido.id}</td>
              <td>
                <div className="orders-table-cliente">
                  {pedido.usuario?.nombre || pedido.cliente || '—'}
                </div>
              </td>
              <td className="orders-table-fecha">
                {pedido.fecha ? new Date(pedido.fecha).toLocaleString('es-MX', {
                  day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit'
                }) : '—'}
              </td>
              <td><StatusBadge estado={pedido.estado} /></td>
              <td className="orders-table-total">${Number(pedido.total).toFixed(2)}</td>
              <td className="orders-table-acciones">
                {acciones ? acciones(pedido) : '—'}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default OrdersTable
