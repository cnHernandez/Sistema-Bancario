package entidad;

public class tipo_de_movimiento {

	private int id_tipo_movimiento;
	private String tipo_de_movimiento;
	
	public int getId_tipo_movimiento() {
		return id_tipo_movimiento;
	}
	public void setId_tipo_movimiento(int id_tipo_movimiento) {
		this.id_tipo_movimiento = id_tipo_movimiento;
	}
	public String getTipo_de_movimiento() {
		return tipo_de_movimiento;
	}
	public void setTipo_de_movimiento(String tipo_de_movimiento) {
		this.tipo_de_movimiento = tipo_de_movimiento;
	}
	
	@Override
	public String toString() {
		return "tipo_de_movimiento [id_tipo_movimiento=" + id_tipo_movimiento + ", tipo_de_movimiento="
				+ tipo_de_movimiento + "]";
	}
	
	
}
