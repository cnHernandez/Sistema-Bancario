package entidad;

public class usuario {
	
	private int id_usuario;
	private String nombre;
	private String contrase�a;
	private int tipo_usuario;
	private Boolean estado;
	
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrase�a() {
		return contrase�a;
	}
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	
	public int getTipo_usuario() {
		return tipo_usuario;
	}
	public void setTipo_usuario(int tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "usuario [id_usuario=" + id_usuario + ", nombre=" + nombre + ", contrase�a=" + contrase�a
				+ ", tipo_usuario=" + tipo_usuario + ", estado=" + estado + "]";
	}
}
