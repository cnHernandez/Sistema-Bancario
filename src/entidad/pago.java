package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class pago {
	 private int idPago;
	    private cuotas idCuota;
	    private cuenta idCuenta;
	    private Date fechaPago;
	    private BigDecimal monto;
		public int getIdPago() {
			return idPago;
		}
		public void setIdPago(int idPago) {
			this.idPago = idPago;
		}
		public cuotas getIdCuota() {
			return idCuota;
		}
		public void setIdCuota(cuotas idCuota) {
			this.idCuota = idCuota;
		}
		public cuenta getIdCuenta() {
			return idCuenta;
		}
		public void setIdCuenta(cuenta idCuenta) {
			this.idCuenta = idCuenta;
		}
		public Date getFechaPago() {
			return fechaPago;
		}
		public void setFechaPago(Date fechaPago) {
			this.fechaPago = fechaPago;
		}
		public BigDecimal getMonto() {
			return monto;
		}
		public void setMonto(BigDecimal monto) {
			this.monto = monto;
		}
	    
	    
}
