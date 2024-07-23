package entidad;

import java.util.Date;

public class prestamo {

	   private int idPrestamo;
	    private cliente cliente; // Composición: Referencia a Cliente
	    private solicitudPrestamo solicitudPrestamo; // Composición: Referencia a SolicitudPrestamo
	    private Date fecha;
	    private double importeTotal;
	    private double importePedido;
	    private int plazoMeses;
	    private double montoCuota;
	    private cuenta cuenta; // Composición: Referencia a Cuenta
	    private boolean autorizado;
	    public int getIdPrestamo() {
			return idPrestamo;
		}
		public void setIdPrestamo(int idPrestamo) {
			this.idPrestamo = idPrestamo;
		}
		public cliente getCliente() {
			return cliente;
		}
		public void setCliente(cliente cliente) {
			this.cliente = cliente;
		}
		public solicitudPrestamo getSolicitudPrestamo() {
			return solicitudPrestamo;
		}
		public void setSolicitudPrestamo(solicitudPrestamo solicitudPrestamo) {
			this.solicitudPrestamo = solicitudPrestamo;
		}
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		public double getImporteTotal() {
			return importeTotal;
		}
		public void setImporteTotal(double importeTotal) {
			this.importeTotal = importeTotal;
		}
		public int getPlazoMeses() {
			return plazoMeses;
		}
		public void setPlazoMeses(int plazoMeses) {
			this.plazoMeses = plazoMeses;
		}
		public double getMontoCuota() {
			return montoCuota;
		}
		public void setMontoCuota(double montoCuota) {
			this.montoCuota = montoCuota;
		}
		public cuenta getCuenta() {
			return cuenta;
		}
		public void setCuenta(cuenta cuenta) {
			this.cuenta = cuenta;
		}
		public boolean isAutorizado() {
			return autorizado;
		}
		public void setAutorizado(boolean autorizado) {
			this.autorizado = autorizado;
		}
		public double getImportePedido() {
			return importePedido;
		}
		public void setImportePedido(double importePedido) {
			this.importePedido = importePedido;
		}
	    
	    
		
	    
}
