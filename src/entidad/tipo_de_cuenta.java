package entidad;

public class tipo_de_cuenta {
	private int id_tipo_cuenta;
	private String tipo_cuenta;
	
	public int getId_tipo_cuenta() {
		return id_tipo_cuenta;
	}
	public void setId_tipo_cuenta(int id_tipo_cuenta) {
		this.id_tipo_cuenta = id_tipo_cuenta;
	}
	public String getTipo_cuenta() {
		return tipo_cuenta;
	}
	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}
	@Override
	public String toString() {
		return "tipo_de_cuenta [id_tipo_cuenta=" + id_tipo_cuenta + ", tipo_cuenta=" + tipo_cuenta + "]";
	}
	
}
