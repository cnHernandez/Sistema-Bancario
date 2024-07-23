package entidad;

import java.util.Date;

public class cliente {
	private int idCliente;
    private usuario idUsuario;
    private String dni;
    private String cuil;
    private String nombre;
    private String apellido;
    private String sexo;
    private String nacionalidad;
    private Date fechaNacimiento;
    private direccion direccion;  
    private String correoElectronico;
    private String telefono;
    private boolean estado;
	
   
    public usuario getUsuario() {
    	return idUsuario;
    }

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdUsuario() {
		return idUsuario.getId_usuario();
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario.setId_usuario(idUsuario);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(direccion direccion) {
		this.direccion = direccion;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	public cliente(){
		this.idUsuario = new usuario();
		
	}
	public cliente(int idCliente, int idUsuario, String dni, String cuil, String nombre, String apellido, String sexo,
			String nacionalidad, Date fechaNacimiento, direccion direccion, String correoElectronico,
			String telefono, boolean estado) {
		super();
		this.idCliente = idCliente;
		this.idUsuario = new usuario();
		this.idUsuario.setId_usuario(idUsuario);
		this.dni = dni;
		this.cuil = cuil;
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.estado = estado;
	}
   
    
}
