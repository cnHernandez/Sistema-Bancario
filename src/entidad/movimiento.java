package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class movimiento {

	    private int idMovimiento;
	    private cuenta idCuenta;
	    private tipo_de_movimiento tipo_de_movimiento;
	    private Date fecha;
	    private String detalle;
	    


		private BigDecimal importe;
	    
		public movimiento(int idMovimiento, cuenta idCuenta, tipo_de_movimiento tipo_de_movimiento, Date fecha,
				String detalle, BigDecimal importe) {
			
			this.idMovimiento = idMovimiento;
			idCuenta = new cuenta();
			this.idCuenta = idCuenta;
			tipo_de_movimiento = new tipo_de_movimiento();
			this.tipo_de_movimiento = tipo_de_movimiento;
			this.fecha = fecha;
			this.detalle = detalle;
			this.importe = importe;
		}
		public movimiento() {
			
			
			tipo_de_movimiento = new tipo_de_movimiento();
			idCuenta = new cuenta ();
		}
		
		
		
		
		
		
		public int getIdMovimiento() {
			return idMovimiento;
		}


		public void setIdMovimiento(int idMovimiento) {
			this.idMovimiento = idMovimiento;
		}


		public int getIdCuenta() {
			return idCuenta.getIdCuenta();
		}


		public void setIdCuenta(int idCuenta) {
			this.idCuenta.setIdCuenta(idCuenta);;
		}


		public int getId_tipo_movimiento() {
			return tipo_de_movimiento.getId_tipo_movimiento();
		}


		public void setId_tipo_movimiento(int id_tipo_movimiento) {
			this.tipo_de_movimiento.setId_tipo_movimiento(id_tipo_movimiento); 
		}


		public Date getFecha() {
			return fecha;
		}


		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}


		public String getDetalle() {
			return detalle;
		}


		public void setDetalle(String detalle) {
			this.detalle = detalle;
		}


		public BigDecimal getImporte() {
			return importe;
		}


		public void setImporte(BigDecimal importeDecimal) {
			this.importe = importeDecimal;
		}


		@Override
		public String toString() {
			return "movimiento [idMovimiento=" + idMovimiento + ", idCuenta=" + idCuenta.getIdCuenta() + ", id_tipo_movimiento="
					+ tipo_de_movimiento.getId_tipo_movimiento() + ", fecha=" + fecha + ", detalle=" + detalle + ", importe=" + importe + "]";
		}

	 
}
