package entidad;

public class direccion {

	 private int idDireccion;
	    private String calle;
	    private String numero;
	    private localidad localidad;
	    
	    public direccion() {
	    }
	    
		public int getIdDireccion() {
			return idDireccion;
		}
		public void setIdDireccion(int idDireccion) {
			this.idDireccion = idDireccion;
		}
		public String getCalle() {
			return calle;
		}
		public void setCalle(String calle) {
			this.calle = calle;
		}
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		public localidad getLocalidad() {
			return localidad;
		}
		public void setLocalidad(localidad localidad) {
			this.localidad = localidad;
		}
		public direccion(int idDireccion, String calle, String numero, entidad.localidad localidad) {
			super();
			this.idDireccion = idDireccion;
			this.calle = calle;
			this.numero = numero;
			this.localidad = localidad;
		}
	    
	  
	    
}
