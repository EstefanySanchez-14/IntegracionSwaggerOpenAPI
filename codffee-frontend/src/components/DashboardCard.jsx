function DashboardCard({ icon, label, value, sublabel, colorClass = '', onClick }) {
  return (
    <div className={`dashboard-card ${colorClass}`} onClick={onClick} role={onClick ? 'button' : undefined}>
      <div className="dashboard-card-header">
        <span className="material-symbols-outlined dashboard-card-icon">{icon}</span>
        {sublabel && <span className="dashboard-card-sublabel">{sublabel}</span>}
      </div>
      <div className="dashboard-card-body">
        <p className="dashboard-card-label">{label}</p>
        <p className="dashboard-card-value">{value}</p>
      </div>
    </div>
  )
}

export default DashboardCard
