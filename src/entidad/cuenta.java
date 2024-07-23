package entidad;

import java.util.Date;

public class cuenta {

	  	private int idCuenta;
	    private int idCliente;
	    private int id_tipo_cuenta;
	    private Date fechaCreacion;
	    private String numeroCuenta;
	    private String cbu;
	    private float saldo;
	    private boolean estado;
		
	    
	    public int getIdCuenta() {
			return idCuenta;
		}
		public void setIdCuenta(int idCuenta) {
			this.idCuenta = idCuenta;
		}
		public int getIdCliente() {
			return idCliente;
		}
		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}
		public Date getFechaCreacion() {
			return fechaCreacion;
		}
		public void setFechaCreacion(Date fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}
		public String getNumeroCuenta() {
			return numeroCuenta;
		}
		public void setNumeroCuenta(String numeroCuenta) {
			this.numeroCuenta = numeroCuenta;
		}
		public String getCbu() {
			return cbu;
		}
		public void setCbu(String cbu) {
			this.cbu = cbu;
		}
		public float getSaldo() {
			return saldo;
		}
		public void setSaldo(float saldo) {
			this.saldo = saldo;
		}
		public int getId_tipo_cuenta() {
			return id_tipo_cuenta;
		}
		public void setId_tipo_cuenta(int id_tipo_cuenta) {
			this.id_tipo_cuenta = id_tipo_cuenta;
		}
		
		public boolean getEstado() {
			return estado;
		}
		public void setEstado(boolean estado) {
			this.estado = estado;
		}
		@Override
		public String toString() {
			return "cuenta [idCuenta=" + idCuenta + ", idCliente=" + idCliente + ", id_tipo_cuenta=" + id_tipo_cuenta
					+ ", fechaCreacion=" + fechaCreacion + ", numeroCuenta=" + numeroCuenta + ", cbu=" + cbu
					+ ", saldo=" + saldo + "]";
		}
	    		
	    
}
