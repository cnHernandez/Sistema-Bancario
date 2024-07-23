package entidad;

public class transferencia {

	private int idTransferencia;
    private movimiento idMovimientoOrigen;
    private movimiento idMovimientoDestino;
    
	public int getIdTransferencia() {
		return idTransferencia;
	}
	public void setIdTransferencia(int idTransferencia) {
		this.idTransferencia = idTransferencia;
	}
	public int getIdMovimientoOrigen() {
		return idMovimientoOrigen.getIdMovimiento();
	}
	public void setIdMovimientoOrigen(int idMovimientoOrigen) {
		this.idMovimientoOrigen.setIdMovimiento(idMovimientoOrigen);
	}
	public int getIdMovimientoDestino() {
		return idMovimientoDestino.getIdMovimiento();
	}
	public void setIdMovimientoDestino(int idMovimientoDestino) {
		this.idMovimientoDestino.setIdMovimiento(idMovimientoDestino);
	}
	
	@Override
	public String toString() {
		return "transferencia [idTransferencia=" + idTransferencia + ", idMovimientoOrigen=" + idMovimientoOrigen.getIdMovimiento()
				+ ", idMovimientoDestino=" + idMovimientoDestino.getIdMovimiento() + "]";
	}
    
	
}
