package entidad;

import java.sql.Date;

public class solicitudPrestamo {

	    private int idSolicitud;
	    private cliente cliente; // Composición: Referencia a Cliente
	    private cuenta cuenta; // Composición: Referencia a Cuenta
	    private Date fecha;
	    private double importePedido;
	    private int plazoMeses;
	    private String estadoSolicitud; // "pendiente", "aprobado", "rechazado"
	    private double tasaInteres;
	    
	    public solicitudPrestamo() {
	        this.cliente = new cliente(); // Inicializar cliente para evitar null
	        this.cuenta = new cuenta(); // Inicializar cuenta para evitar null
	    }
	    
	    public int getIdSolicitud() {
			return idSolicitud;
		}
		public void setIdSolicitud(int idSolicitud) {
			this.idSolicitud = idSolicitud;
		}
		public cliente getCliente() {
			return cliente;
		}
		public void setCliente(cliente cliente) {
			this.cliente = cliente;
		}
		public cuenta getCuenta() {
			return cuenta;
		}
		public void setCuenta(cuenta cuenta) {
			this.cuenta = cuenta;
		}
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(java.util.Date date) {
			this.fecha = (Date) date;
		}
		public double getImportePedido() {
			return importePedido;
		}
		public void setImportePedido(double importePedido) {
			this.importePedido = importePedido;
		}
		public int getPlazoMeses() {
			return plazoMeses;
		}
		public void setPlazoMeses(int plazoMeses) {
			this.plazoMeses = plazoMeses;
		}
		public String getEstadoSolicitud() {
			return estadoSolicitud;
		}
		public void setEstadoSolicitud(String estadoSolicitud) {
			this.estadoSolicitud = estadoSolicitud;
		}

		public double getTasaInteres() {
			return tasaInteres;
		}

		public void setTasaInteres(double tasaInteres) {
			this.tasaInteres = tasaInteres;
		}
	
}
